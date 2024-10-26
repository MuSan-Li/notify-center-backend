package com.xiao.notify.model.domain.notify.request;

import com.xiao.notify.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 订阅配置表 page
 *
 * @author lh
 * @since 2024-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyConfigPageReq extends PageRequest implements Serializable {

    //订阅名称
    private String name;
}
