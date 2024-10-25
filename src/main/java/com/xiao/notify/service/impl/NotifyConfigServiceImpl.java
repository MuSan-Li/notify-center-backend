package com.xiao.notify.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.common.enums.NotifyTypeEnum;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.mapper.NotifyConfigMapper;
import com.xiao.notify.model.domain.notify.request.SendNotifyRequest;
import com.xiao.notify.model.domain.user.SafetyUser;
import com.xiao.notify.model.entity.EmailConfig;
import com.xiao.notify.model.entity.NotifyConfig;
import com.xiao.notify.model.entity.NotifyInfoLog;
import com.xiao.notify.service.EmailConfigService;
import com.xiao.notify.service.NotifyConfigService;
import com.xiao.notify.service.NotifyInfoLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.xiao.notify.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 订阅配置表(SendNotifyRequest)表服务实现类
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@Slf4j
@Service
public class NotifyConfigServiceImpl
        extends ServiceImpl<NotifyConfigMapper, NotifyConfig> implements NotifyConfigService {

    @Resource
    private NotifyInfoLogService notifyInfoLogService;
    @Resource
    private EmailConfigService emailConfigService;

    @Resource
    private JavaMailSender mailSender;

    @Override
    public boolean sendNotify(SendNotifyRequest notifyRequest, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        SafetyUser safetyUser = (SafetyUser) userObj;
        if (Objects.isNull(notifyRequest) || notifyRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        NotifyConfig notifyConfig = this.getById(notifyRequest.getId());
        if (Objects.isNull(notifyConfig)) {
            throw new BusinessException(ErrorCode.DATA_IS_EMPTY, "通知配置信息不存在");
        }

        NotifyTypeEnum notifyTypeEnum = NotifyTypeEnum.getNotifyTypeByCode(notifyConfig.getNotifyType());
        switch (notifyTypeEnum) {
            case EMAIL:
                EmailConfig emailConfig = emailConfigService.getEmailConfig();
                if (Objects.isNull(emailConfig)) {
                    throw new BusinessException(ErrorCode.DATA_IS_EMPTY, "邮件配置信息不存在");
                }
                // 发送邮件
                boolean sendEmail = sendEmail(notifyConfig, emailConfig, safetyUser);
                NotifyInfoLog notifyInfoLog = new NotifyInfoLog();
                notifyInfoLog.setStatus(sendEmail ? 1 : 0);
                notifyInfoLog.setNotifyType(notifyTypeEnum.getCode());
                boolean saved = notifyInfoLogService.save(notifyInfoLog);
                log.debug("saved:{}", saved);
                break;
            case SMS:
                // 短信发送
            default:
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }

    public boolean sendEmail(NotifyConfig notifyConfig, EmailConfig emailConfig, SafetyUser safetyUser) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StringPool.UTF_8);
            helper.setFrom(emailConfig.getUsername());
            helper.setTo(safetyUser.getEmail());
            helper.setSubject("小小订阅通知");
            helper.setText(notifyConfig.getContent(), true);
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            log.error("邮件发送失败，notifyConfig：{}，emailConfig：{}", notifyConfig, emailConfig, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件发送失败");
        }
    }
}

