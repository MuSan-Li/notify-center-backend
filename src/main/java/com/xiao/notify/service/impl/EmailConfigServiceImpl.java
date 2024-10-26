package com.xiao.notify.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.notify.mapper.EmailConfigMapper;
import com.xiao.notify.model.domain.email.request.EmailConfigPageReq;
import com.xiao.notify.model.entity.EmailConfig;
import com.xiao.notify.model.domain.email.response.EmailConfigResp;
import com.xiao.notify.service.EmailConfigService;
import com.xiao.notify.utils.PageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * 邮箱配置表(EmailConfig)表服务实现类
 *
 * @author lh
 * @since 2024-10-22 21:29:46
 */
@Slf4j
@Service
public class EmailConfigServiceImpl
        extends ServiceImpl<EmailConfigMapper, EmailConfig> implements EmailConfigService {

    @Override
    public EmailConfig getEmailConfig() {
        return getRandomEmailConfig();
    }

    private EmailConfig getRandomEmailConfig() {
        LambdaQueryWrapper<EmailConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.select(
                EmailConfig::getHost,
                EmailConfig::getPort,
                EmailConfig::getUsername,
                EmailConfig::getPassword,
                EmailConfig::getProps
        );
        wrapper.eq(EmailConfig::getIsDelete, 0)
                .last("ORDER BY RAND() LIMIT 1");
        return getOne(wrapper);
    }


    @Override
    public JavaMailSender getMailSender() {
        EmailConfig emailConfig = getRandomEmailConfig();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfig.getHost());
        // mailSender.setPort(Integer.parseInt(emailConfig.getPort()));
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        // props.put("mail.properties.mail.smtp.port", "587");
        // props.put("mail.properties.mail.smtp.auth", "true");
        // props.put("mail.properties.mail.smtp.ssl.enable", "true");
        // props.put("mail.properties.mail.smtp.starttls.enable", "true");
        // props.put("mail.properties.mail.smtp.starttls.required", "true");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }

    @Override
    public JavaMailSender getMailSender(EmailConfig emailConfig) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfig.getHost());
        // mailSender.setPort(Integer.parseInt(emailConfig.getPort()));
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        // props.put("mail.properties.mail.smtp.port", "587");
        // props.put("mail.properties.mail.smtp.auth", "true");
        // props.put("mail.properties.mail.smtp.ssl.enable", "true");
        // props.put("mail.properties.mail.smtp.starttls.enable", "true");
        // props.put("mail.properties.mail.smtp.starttls.required", "true");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }

    @Override
    public Page<EmailConfigResp> getByPage(EmailConfigPageReq req, HttpServletRequest request) {
        LambdaQueryWrapper<EmailConfig> wrapper = Wrappers.lambdaQuery();
        String username = req.getUsername();
        int current = req.getCurrent();
        int pageSize = req.getPageSize();
        wrapper.like(StrUtil.isNotBlank(username), EmailConfig::getUsername, username);
        Page<EmailConfig> page = page(new Page<>(current, pageSize), wrapper);
        return PageConverter.convertPage(page, EmailConfigResp.class);
    }


}

