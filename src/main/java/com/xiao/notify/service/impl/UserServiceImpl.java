package com.xiao.notify.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.mapper.UserMapper;
import com.xiao.notify.model.domain.user.SafetyUser;
import com.xiao.notify.model.entity.UserInfo;
import com.xiao.notify.model.domain.user.request.UserAddRequest;
import com.xiao.notify.model.domain.user.request.UserQueryRequest;
import com.xiao.notify.model.domain.user.request.UserUpdateRequest;
import com.xiao.notify.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiao.notify.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author lh
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "xiao";

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度小于4位");
        }
        if (userAccount.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度大于20位");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (userPassword.length() > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码长度大于30位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户名不能包含特殊字符");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不同");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(UserInfo::getUserAccount, userAccount);
            long count = userMapper.selectCount(wrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            UserInfo userInfo = new UserInfo();
            userInfo.setUserAccount(userAccount);
            userInfo.setUserPassword(encryptPassword);
            boolean saveResult = this.save(userInfo);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户注册失败，请联系管理员处理");
            }
            return userInfo.getId();
        }
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    @Override
    public SafetyUser userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或者密码不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度不能小于4位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能小于8位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能包含特殊字符");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getUserAccount, userAccount).eq(UserInfo::getUserPassword, encryptPassword);
        UserInfo userInfo = userMapper.selectOne(wrapper);
        if (Objects.isNull(userInfo)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        // 3. 用户脱敏
        SafetyUser safetyUser = getSafetyUser(userInfo);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUserInfo
     * @return
     */
    @Override
    public SafetyUser getSafetyUser(UserInfo originUserInfo) {
        if (Objects.isNull(originUserInfo)) {
            return null;
        }
        SafetyUser user = new SafetyUser();
        user.setId(originUserInfo.getId());
        user.setUsername(originUserInfo.getUsername());
        user.setUserAccount(originUserInfo.getUserAccount());
        user.setAvatarUrl(originUserInfo.getAvatarUrl());
        user.setGender(originUserInfo.getGender());
        user.setPhone(originUserInfo.getPhone());
        user.setEmail(originUserInfo.getEmail());
        user.setUserRole(originUserInfo.getUserRole());
        user.setUserStatus(originUserInfo.getUserStatus());
        return user;
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public void userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
    }

    @Override
    public Wrapper<UserInfo> getQueryWrapper(UserQueryRequest userQueryRequest) {
        Long id = userQueryRequest.getId();
        String userName = userQueryRequest.getUserName();
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Objects.nonNull(id), UserInfo::getId, id)
                .like(StrUtil.isNotBlank(userName), UserInfo::getUsername, userName);
        return wrapper;
    }

    @Override
    public long addUser(UserAddRequest request) {
        String username = request.getUsername();
        String userAccount = request.getUserAccount();
        Integer gender = request.getGender();
        String email = request.getEmail();
        String phone = request.getPhone();
        // 1. 校验
        if (ObjUtil.hasEmpty(username, gender, email, phone)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度不能小于4位");
        }
        if (userAccount.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度大于20位");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setUserAccount(userAccount);
        userInfo.setGender(gender);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setUserRole(0);
        userInfo.setUserStatus(0);
        userInfo.setAvatarUrl("https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg");
        userInfo.setUserPassword(DigestUtils.md5DigestAsHex((SALT + "123456").getBytes()));
        synchronized (userInfo.toString().intern()) {
            int inserted = baseMapper.insert(userInfo);
            if (inserted <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户创建失败");
            }
        }
        return userInfo.getId();
    }

    @Override
    public boolean updateUser(UserUpdateRequest request) {
        Long id = request.getId();
        String username = request.getUsername();
        Integer gender = request.getGender();
        String email = request.getEmail();
        String phone = request.getPhone();
        // 1. 校验
        if (ObjUtil.isAllEmpty(username, gender, email, phone)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setUsername(username);
        userInfo.setGender(gender);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        synchronized (userInfo.toString().intern()) {
            int updated = baseMapper.updateById(userInfo);
            if (updated <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户更新失败");
            }
        }
        return true;
    }

}

