package com.xiao.notify.model.domain.user.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户新增请求体
 *
 * @author lh
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

}

