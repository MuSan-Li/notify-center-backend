package com.xiao.notify.model.domain.notify.request;

import lombok.Data;

import java.io.Serializable;


/**
 * 发送订阅通知
 *
 * @author lh
 * @since 2024-10-22
 */
@Data
public class SendNotifyRequest implements Serializable {
    // 订阅通知id
    private Long id;
}
