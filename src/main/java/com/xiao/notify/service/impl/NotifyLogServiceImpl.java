package com.xiao.notify.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.notify.mapper.NotifyLogMapper;
import com.xiao.notify.model.domain.notify.request.NotifyLogPageReq;
import com.xiao.notify.model.domain.notify.response.NotifyLogResp;
import com.xiao.notify.model.entity.NotifyLog;
import com.xiao.notify.service.NotifyLogService;
import com.xiao.notify.utils.PageConverter;
import com.xiao.notify.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 订阅信息记录表(NotifyLog)表服务实现类
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@Slf4j
@Service
public class NotifyLogServiceImpl
        extends ServiceImpl<NotifyLogMapper, NotifyLog> implements NotifyLogService {


    @Override
    public Page<NotifyLogResp> getByPage(NotifyLogPageReq req, HttpServletRequest request) {
        LambdaQueryWrapper<NotifyLog> wrapper = Wrappers.lambdaQuery();
        Long notifyConfigId = req.getNotifyConfigId();
        int current = req.getCurrent();
        int pageSize = req.getPageSize();
        wrapper.like(Objects.nonNull(notifyConfigId) && notifyConfigId > 0,
                NotifyLog::getNotifyConfigId, notifyConfigId);
        if (!UserUtils.isAdmin(request)) {
            wrapper.eq(NotifyLog::getUserId, UserUtils.getCurrentUser(request).getId());
        }
        Page<NotifyLog> page = page(new Page<>(current, pageSize), wrapper);
        return PageConverter.convertPage(page, NotifyLogResp.class);
    }
}

