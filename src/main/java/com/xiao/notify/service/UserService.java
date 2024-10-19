package com.xiao.notify.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao.notify.model.domain.SafetyUser;
import com.xiao.notify.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author lh
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    SafetyUser userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    SafetyUser getSafetyUser(User originUser);


    /**
     * 用户注销
     *
     * @param request
     */
    void userLogout(HttpServletRequest request);
}
