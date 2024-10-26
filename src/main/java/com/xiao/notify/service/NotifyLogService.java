package com.xiao.notify.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao.notify.model.domain.notify.request.NotifyLogPageReq;
import com.xiao.notify.model.domain.notify.response.NotifyLogResp;
import com.xiao.notify.model.entity.NotifyLog;

import javax.servlet.http.HttpServletRequest;

/**
 * 订阅信息记录表(NotifyLog)表服务接口
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
public interface NotifyLogService extends IService<NotifyLog> {

    /**
     * 获取分页数据
     *
     * @param req
     * @param request
     * @return
     */
    Page<NotifyLogResp> getByPage(NotifyLogPageReq req, HttpServletRequest request);
}

