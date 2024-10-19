package com.xiao.notify.exception;

import cn.hutool.core.util.StrUtil;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author lh
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        String msg = StrUtil.isNotBlank(e.getDescription()) ? e.getDescription() : e.getMessage();
        return ResultUtils.error(e.getCode(), msg);
    }


    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
}
