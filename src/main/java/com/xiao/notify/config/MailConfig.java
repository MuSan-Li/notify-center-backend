package com.xiao.notify.config;

import com.xiao.notify.model.entity.EmailConfig;
import com.xiao.notify.service.EmailConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Resource
    private EmailConfigService emailConfigService;

    @Bean
    public JavaMailSender mailSender() {
        EmailConfig emailConfig = emailConfigService.getEmailConfig();
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
}