package com.xiao.notify.model.domain.user.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户删除请求体
 *
 * @author lh
 */
@Data
public class UserDeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;
}

