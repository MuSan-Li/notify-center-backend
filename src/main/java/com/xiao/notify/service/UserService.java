package com.xiao.notify.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiao.notify.model.domain.user.SafetyUser;
import com.xiao.notify.model.entity.UserInfo;
import com.xiao.notify.model.domain.user.request.UserAddRequest;
import com.xiao.notify.model.domain.user.request.UserQueryRequest;
import com.xiao.notify.model.domain.user.request.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author lh
 */
public interface UserService extends IService<UserInfo> {

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
     * @param originUserInfo
     * @return
     */
    SafetyUser getSafetyUser(UserInfo originUserInfo);


    /**
     * 用户注销
     *
     * @param request
     */
    void userLogout(HttpServletRequest request);

    /**
     * 组装wapper对象
     *
     * @param userQueryRequest
     * @return
     */
    Wrapper<UserInfo> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 创建用户
     *
     * @param request
     * @return
     */
    long addUser(UserAddRequest request);

    /**
     * 更新用户信息
     *
     * @param request
     * @return
     */
    boolean updateUser(UserUpdateRequest request);
}
