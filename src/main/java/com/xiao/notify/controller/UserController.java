package com.xiao.notify.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.common.ResultUtils;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.model.domain.SafetyUser;
import com.xiao.notify.model.domain.User;
import com.xiao.notify.model.domain.request.UserLoginRequest;
import com.xiao.notify.model.domain.request.UserRegisterRequest;
import com.xiao.notify.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xiao.notify.contant.UserConstant.ADMIN_ROLE;
import static com.xiao.notify.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author lh
 */
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = request.getUserAccount();
        String userPassword = request.getUserPassword();
        String checkPassword = request.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param loginRequest
     * @param request
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResponse<SafetyUser> userLogin(@RequestBody UserLoginRequest loginRequest,
                                              HttpServletRequest request) {
        if (Objects.isNull(loginRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = loginRequest.getUserAccount();
        String userPassword = loginRequest.getUserPassword();
        SafetyUser user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "用户注销")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.userLogout(request);
        return ResultUtils.success(null);
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取当前用户")
    @GetMapping("/current")
    public BaseResponse<SafetyUser> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        SafetyUser currentUser = (SafetyUser) userObj;
        if (Objects.isNull(currentUser)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        SafetyUser safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 用户查询
     *
     * @param userAccount
     * @param request
     * @return
     */
    @ApiOperation(value = "用户查询")
    @GetMapping("/search")
    public BaseResponse<List<SafetyUser>> searchUsers(String userAccount, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(userAccount), User::getUserAccount, userAccount);
        List<User> userList = userService.list(wrapper);
        List<SafetyUser> list = userList.stream()
                .map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    /**
     * 删除用户
     *
     * @param id
     * @param request
     * @return
     */
    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        boolean flag = userService.removeById(id);
        return ResultUtils.success(flag);
    }


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        SafetyUser safetyUser = (SafetyUser) userObj;
        return Objects.nonNull(safetyUser) && safetyUser.getUserRole() == ADMIN_ROLE;
    }

}