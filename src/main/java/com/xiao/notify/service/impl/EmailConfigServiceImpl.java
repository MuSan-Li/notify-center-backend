package com.xiao.notify.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.notify.mapper.EmailConfigMapper;
import com.xiao.notify.model.entity.EmailConfig;
import com.xiao.notify.service.EmailConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                EmailConfig::getConfig
        );
        wrapper.eq(EmailConfig::getIsDelete, 0)
                .last("ORDER BY RAND() LIMIT 1");
        return getOne(wrapper);
    }


}

