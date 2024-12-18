package com.xiao.notify.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao.notify.model.domain.notify.request.NotifyConfigPageReq;
import com.xiao.notify.model.domain.notify.request.NotifyConfigRequest;
import com.xiao.notify.model.domain.notify.request.NotifySendRequest;
import com.xiao.notify.model.domain.notify.response.NotifyConfigResp;
import com.xiao.notify.model.domain.user.SafetyUser;
import com.xiao.notify.model.entity.NotifyConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订阅配置表(NotifySendRequest)表服务接口
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
public interface NotifyConfigService extends IService<NotifyConfig> {

    /**
     * 发送订阅通知
     *
     * @param notifyRequest
     * @return
     */
    boolean sendNotify(NotifySendRequest notifyRequest, SafetyUser safetyUser);


    /**
     * 发送订阅通知
     *
     * @param notifyRequest
     * @param request
     * @return
     */
    boolean sendNotify(NotifySendRequest notifyRequest, HttpServletRequest request);


    /**
     * 获取分页数据
     *
     * @param req
     * @param request
     * @return
     */
    Page<NotifyConfigResp> getByPage(NotifyConfigPageReq req, HttpServletRequest request);

    /**
     * 获取状态开启的订阅列表
     *
     * @return
     */
    List<NotifyConfig> getNotifyConfigActiveList();

    /**
     * 更新订阅信息
     *
     * @param updateRequest
     * @return
     */
    boolean updateNotifyConfig(NotifyConfigRequest updateRequest);
}

