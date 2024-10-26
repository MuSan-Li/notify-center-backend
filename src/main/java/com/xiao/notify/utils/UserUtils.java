package com.xiao.notify.utils;

import com.xiao.notify.model.domain.user.SafetyUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.xiao.notify.contant.UserConstant.ADMIN_ROLE;
import static com.xiao.notify.contant.UserConstant.USER_LOGIN_STATE;

public class UserUtils {

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    public static boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        SafetyUser safetyUser = (SafetyUser) userObj;
        return Objects.nonNull(safetyUser) && safetyUser.getUserRole() == ADMIN_ROLE;
    }


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    public static SafetyUser getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        return  (SafetyUser) userObj;
    }
}
