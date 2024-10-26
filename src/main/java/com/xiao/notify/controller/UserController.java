package com.xiao.notify.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.notify.common.BaseResponse;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.common.ResultUtils;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.model.domain.user.SafetyUser;
import com.xiao.notify.model.domain.user.request.UserAddRequest;
import com.xiao.notify.model.domain.user.request.UserDeleteRequest;
import com.xiao.notify.model.domain.user.request.UserLoginRequest;
import com.xiao.notify.model.domain.user.request.UserQueryRequest;
import com.xiao.notify.model.domain.user.request.UserRegisterRequest;
import com.xiao.notify.model.domain.user.request.UserUpdateRequest;
import com.xiao.notify.model.entity.UserInfo;
import com.xiao.notify.service.UserService;
import com.xiao.notify.utils.PageConverter;
import com.xiao.notify.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.xiao.notify.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author lh
 */
// @Api(tags = {"用户管理"})
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
        UserInfo userInfo = userService.getById(userId);
        SafetyUser safetyUser = userService.getSafetyUser(userInfo);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 用户查询
     *
     * @param userAccount
     * @param request
     * @return
     */
    // @ApiOperation(value = "用户查询")
    // @GetMapping("/search")
    // public BaseResponse<List<SafetyUser>> searchUsers(String userAccount, HttpServletRequest request) {
    //     if (!UserUtils.isAdmin(request)) {
    //         throw new BusinessException(ErrorCode.NO_AUTH);
    //     }
    //     LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
    //     wrapper.like(StrUtil.isNotBlank(userAccount), UserInfo::getUserAccount, userAccount);
    //     List<UserInfo> userInfoList = userService.list(wrapper);
    //     List<SafetyUser> list = userInfoList.stream()
    //             .map(userInfo -> userService.getSafetyUser(userInfo)).collect(Collectors.toList());
    //     return ResultUtils.success(list);
    // }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest
     * @return
     */
    @ApiOperation(value = "用户分页查询")
    @PostMapping("/list/page")
    public BaseResponse<Page<SafetyUser>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest,
                                                         HttpServletRequest request) {
        if (!UserUtils.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (Objects.isNull(userQueryRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<UserInfo> userPage = userService.page(new Page<UserInfo>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        Page<SafetyUser> convertPage = PageConverter.convertPage(userPage, SafetyUser.class);
        return ResultUtils.success(convertPage);
    }

    /**
     * 添加用户
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "添加用户")
    @PostMapping("/addUser")
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest addRequest, HttpServletRequest request) {
        if (!UserUtils.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (Objects.isNull(addRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.addUser(addRequest);
        return ResultUtils.success(result);
    }

    /**
     * 更新用户
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "更新用户")
    @PostMapping("/updateUser")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest updateRequest, HttpServletRequest request) {
        if (!UserUtils.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (Objects.isNull(updateRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.updateUser(updateRequest);
        return ResultUtils.success(result);
    }

    /**
     * 删除用户
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest deleteRequest,
                                            HttpServletRequest request) {
        if (!UserUtils.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (Objects.isNull(deleteRequest) || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean flag = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(flag);
    }
}
