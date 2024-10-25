package com.xiao.notify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.notify.mapper.NotifyInfoLogMapper;
import com.xiao.notify.model.entity.NotifyInfoLog;
import com.xiao.notify.service.NotifyInfoLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订阅信息记录表(NotifyInfoLog)表服务实现类
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@Slf4j
@Service
public class NotifyInfoLogServiceImpl
        extends ServiceImpl<NotifyInfoLogMapper, NotifyInfoLog> implements NotifyInfoLogService {

}

