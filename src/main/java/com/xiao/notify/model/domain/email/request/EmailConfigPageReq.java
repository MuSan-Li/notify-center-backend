package com.xiao.notify.model.domain.email.request;

import com.xiao.notify.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 邮箱配置表 page req
 *
 * @author lh
 * @since 2024-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmailConfigPageReq extends PageRequest implements Serializable {

    //订阅名称
    private String username;
}
