package com.xiao.notify.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.common.enums.NotifyTypeEnum;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.job.QuartzUtils;
import com.xiao.notify.mapper.NotifyConfigMapper;
import com.xiao.notify.model.domain.notify.request.NotifyConfigPageReq;
import com.xiao.notify.model.domain.notify.request.NotifyConfigRequest;
import com.xiao.notify.model.domain.notify.request.NotifySendRequest;
import com.xiao.notify.model.domain.notify.response.NotifyConfigResp;
import com.xiao.notify.model.domain.user.SafetyUser;
import com.xiao.notify.model.entity.EmailConfig;
import com.xiao.notify.model.entity.NotifyConfig;
import com.xiao.notify.model.entity.NotifyLog;
import com.xiao.notify.model.entity.UserInfo;
import com.xiao.notify.service.EmailConfigService;
import com.xiao.notify.service.NotifyConfigService;
import com.xiao.notify.service.NotifyLogService;
import com.xiao.notify.service.UserService;
import com.xiao.notify.utils.PageConverter;
import com.xiao.notify.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 订阅配置表(NotifySendRequest)表服务实现类
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@Slf4j
@Service
public class NotifyConfigServiceImpl
        extends ServiceImpl<NotifyConfigMapper, NotifyConfig> implements NotifyConfigService {

    @Resource
    private UserService userService;
    @Resource
    private NotifyLogService notifyLogService;
    @Resource
    private EmailConfigService emailConfigService;
    @Resource
    private Scheduler scheduler;

    @Override
    public boolean sendNotify(NotifySendRequest notifyRequest, SafetyUser safetyUser) {
        NotifyConfig notifyConfig = this.getById(notifyRequest.getId());
        if (Objects.isNull(notifyConfig)) {
            throw new BusinessException(ErrorCode.DATA_IS_EMPTY, "通知配置信息不存在");
        }
        if (Objects.isNull(safetyUser)) {
            Long userId = notifyConfig.getUserId();
            UserInfo userInfo = userService.getById(userId);
            safetyUser = BeanUtil.copyProperties(userInfo, SafetyUser.class);
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
                NotifyLog notifyLog = new NotifyLog();
                notifyLog.setNotifyConfigId(notifyConfig.getId());
                notifyLog.setNotifyConfigName(notifyConfig.getNotifyName());
                notifyLog.setStatus(sendEmail ? 1 : 0);
                notifyLog.setUserId(safetyUser.getId());
                notifyLog.setNotifyType(notifyTypeEnum.getCode());
                boolean saved = notifyLogService.save(notifyLog);
                log.debug("saved:{}", saved);
                break;
            case SMS:
                // 短信发送
            default:
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;
    }

    @Override
    public boolean sendNotify(NotifySendRequest notifyRequest, HttpServletRequest request) {
        SafetyUser safetyUser = UserUtils.getCurrentUser(request);
        return sendNotify(notifyRequest, safetyUser);
    }

    @Override
    public Page<NotifyConfigResp> getByPage(NotifyConfigPageReq req, HttpServletRequest request) {
        LambdaQueryWrapper<NotifyConfig> wrapper = Wrappers.lambdaQuery();
        String name = req.getName();
        int current = req.getCurrent();
        int pageSize = req.getPageSize();
        wrapper.like(StrUtil.isNotBlank(name), NotifyConfig::getNotifyName, name);
        if (!UserUtils.isAdmin(request)) {
            wrapper.eq(NotifyConfig::getUserId, UserUtils.getCurrentUser(request).getId());
        }
        Page<NotifyConfig> page = page(new Page<>(current, pageSize), wrapper);
        return PageConverter.convertPage(page, NotifyConfigResp.class);
    }

    @Override
    public List<NotifyConfig> getNotifyConfigActiveList() {
        LambdaQueryWrapper<NotifyConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(NotifyConfig::getNotifyStatus, 0);
        return list(wrapper);
    }

    @Override
    public boolean updateNotifyConfig(NotifyConfigRequest updateRequest) {
        Long id = updateRequest.getId();
        if (Objects.isNull(id)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        LambdaQueryWrapper<NotifyConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NotifyConfig::getId, id);
        NotifyConfig oldNotifyConfig = getOne(queryWrapper);
        if (Objects.isNull(oldNotifyConfig)) {
            throw new BusinessException(ErrorCode.DATA_IS_EMPTY);
        }
        String corn = updateRequest.getCorn();
        String notifyStatus = updateRequest.getNotifyStatus();
        String remarks = updateRequest.getRemarks();
        String content = updateRequest.getContent();
        Integer notifyType = updateRequest.getNotifyType();
        String notifyName = updateRequest.getNotifyName();
        Optional.ofNullable(corn).ifPresent(oldNotifyConfig::setCorn);
        Optional.ofNullable(notifyStatus).ifPresent(val -> oldNotifyConfig.setNotifyStatus(Integer.valueOf(val)));
        Optional.ofNullable(content).ifPresent(oldNotifyConfig::setContent);
        Optional.ofNullable(notifyType).ifPresent(oldNotifyConfig::setNotifyType);
        Optional.ofNullable(notifyName).ifPresent(oldNotifyConfig::setNotifyName);
        Optional.ofNullable(remarks).ifPresent(oldNotifyConfig::setRemarks);
        boolean result = updateById(oldNotifyConfig);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        QuartzUtils.updateJob(scheduler, oldNotifyConfig);
        return true;
    }

    public boolean sendEmail(NotifyConfig notifyConfig, EmailConfig emailConfig, SafetyUser safetyUser) {
        try {
            JavaMailSender mailSender = emailConfigService.getMailSender(emailConfig);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StringPool.UTF_8);
            helper.setFrom(emailConfig.getUsername());
            helper.setTo(safetyUser.getEmail());
            helper.setSubject(notifyConfig.getNotifyName());
            helper.setText(notifyConfig.getContent(), true);
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            log.error("邮件发送失败，notifyConfig：{}，emailConfig：{}", notifyConfig, emailConfig, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件发送失败");
        }
    }
}

