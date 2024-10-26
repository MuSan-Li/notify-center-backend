package com.xiao.notify.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao.notify.model.domain.email.request.EmailConfigPageReq;
import com.xiao.notify.model.entity.EmailConfig;
import com.xiao.notify.model.domain.email.response.EmailConfigResp;
import org.springframework.mail.javamail.JavaMailSender;

import javax.servlet.http.HttpServletRequest;

/**
 * 邮箱配置表(EmailConfig)表服务接口
 *
 * @author lh
 * @since 2024-10-22 21:29:46
 */
public interface EmailConfigService extends IService<EmailConfig> {

    /**
     * 获取邮件配置
     *
     * @return
     */
    EmailConfig getEmailConfig();


    /**
     * 获取邮箱发送服务
     *
     * @return
     */
    JavaMailSender getMailSender();

    /**
     * 获取邮箱发送服务
     *
     * @return
     */
    JavaMailSender getMailSender(EmailConfig emailConfig);

    /**
     * 获取分页数据
     *
     * @param req
     * @param request
     * @return
     */
    Page<EmailConfigResp> getByPage(EmailConfigPageReq req, HttpServletRequest request);
}

