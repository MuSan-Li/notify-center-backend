package com.xiao.notify.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.model.domain.email.request.EmailConfigPageReq;
import com.xiao.notify.model.domain.email.response.EmailConfigResp;
import com.xiao.notify.service.EmailConfigService;
import com.xiao.notify.utils.UserUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.xiao.notify.common.ResultUtils.success;

/**
 * 邮箱配置表(EmailConfig)表控制层
 *
 * @author lh
 * @since 2024-10-22 21:29:46
 */
@RestController
@RequestMapping("email-config")
public class EmailConfigController {
    /**
     * 服务对象
     */
    @Resource
    private EmailConfigService emailConfigService;

    /**
     * 分页查询所有数据
     *
     * @param req
     * @return 所有数据
     */
    @PostMapping("page")
    public BaseResponse<Page<EmailConfigResp>> getEmailConfigPage(@RequestBody EmailConfigPageReq req,
                                                    HttpServletRequest request) {
        if (!UserUtils.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (Objects.isNull(req)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<EmailConfigResp> page = emailConfigService.getByPage(req, request);
        return success(page);
    }
}

