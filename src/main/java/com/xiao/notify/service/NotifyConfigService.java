package com.xiao.notify.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao.notify.model.domain.notify.request.SendNotifyRequest;
import com.xiao.notify.model.entity.NotifyConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * 订阅配置表(SendNotifyRequest)表服务接口
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
public interface NotifyConfigService extends IService<NotifyConfig> {


    /**
     * 发送订阅通知
     *
     * @param notifyRequest
     * @param request
     * @return
     */
    boolean sendNotify(SendNotifyRequest notifyRequest, HttpServletRequest request);
}

