package com.xiao.notify.model.domain.notify.request;

import com.xiao.notify.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 订阅信息记录表(NotifyLog)表实体类
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyLogPageReq extends PageRequest implements Serializable {

    //订阅配置id
    private Long notifyConfigId;
}

