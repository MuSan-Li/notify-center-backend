package com.xiao.notify.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao.notify.model.entity.EmailConfig;

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
}

