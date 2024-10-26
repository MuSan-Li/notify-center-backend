package com.xiao.notify.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.model.domain.notify.request.NotifyConfigPageReq;
import com.xiao.notify.model.domain.notify.request.NotifyLogPageReq;
import com.xiao.notify.model.domain.notify.response.NotifyConfigResp;
import com.xiao.notify.model.domain.notify.response.NotifyLogResp;
import com.xiao.notify.model.entity.NotifyLog;
import com.xiao.notify.service.NotifyLogService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.xiao.notify.common.ResultUtils.success;

/**
 * 订阅信息记录表(NotifyLog)表控制层
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@RestController
@RequestMapping("notify-info-log")
public class NotifyLogController {
    /**
     * 服务对象
     */
    @Resource
    private NotifyLogService notifyLogService;

    /**
     * 分页查询所有数据
     *
     * @param req
     * @return 所有数据
     */
    @PostMapping("page")
    public BaseResponse<Page<NotifyLogResp>> getNotifyLogPage(@RequestBody NotifyLogPageReq req,
                                                              HttpServletRequest request) {
        if (Objects.isNull(req)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<NotifyLogResp> page = notifyLogService.getByPage(req, request);
        return success(page);
    }
}

