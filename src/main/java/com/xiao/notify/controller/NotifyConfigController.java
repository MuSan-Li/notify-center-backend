package com.xiao.notify.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.common.ResultUtils;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.model.domain.notify.request.NotifyConfigPageReq;
import com.xiao.notify.model.domain.notify.request.NotifyConfigRequest;
import com.xiao.notify.model.domain.notify.request.NotifySendRequest;
import com.xiao.notify.model.domain.notify.response.NotifyConfigResp;
import com.xiao.notify.service.NotifyConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.xiao.notify.common.ResultUtils.success;

/**
 * 订阅配置表(NotifySendRequest)表控制层
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@RestController
@RequestMapping("notify-config")
public class NotifyConfigController {

    /**
     * 服务对象
     */
    @Resource
    private NotifyConfigService notifyConfigService;

    /**
     * 分页查询所有数据
     *
     * @param req
     * @return 所有数据
     */
    @PostMapping("page")
    public BaseResponse<Page<NotifyConfigResp>> getNotifyConfigPage(@RequestBody NotifyConfigPageReq req,
                                                        HttpServletRequest request) {
        if (Objects.isNull(req)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<NotifyConfigResp> page = notifyConfigService.getByPage(req, request);
        return success(page);
    }

    /**
     * 订阅通知
     *
     * @param notifyRequest
     * @return 是否发送成功
     */
    @PostMapping("sendNotify")
    public BaseResponse<Boolean> sendNotify(@RequestBody NotifySendRequest notifyRequest,
                                            HttpServletRequest request) {
        return success(notifyConfigService.sendNotify(notifyRequest, request));
    }


    /**
     * 更新订阅
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "更新订阅")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateNotifyConfig(@RequestBody NotifyConfigRequest updateRequest,
                                                    HttpServletRequest request) {
        // TODO 获取当前用户信息
        if (Objects.isNull(updateRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = notifyConfigService.updateNotifyConfig(updateRequest);
        return ResultUtils.success(result);
    }

}

