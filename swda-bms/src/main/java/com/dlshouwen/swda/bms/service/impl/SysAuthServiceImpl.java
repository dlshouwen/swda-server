package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.auth.mobile.MobileAuthenticationToken;
import com.dlshouwen.swda.auth.third.ThirdAuthenticationToken;
import com.dlshouwen.swda.auth.third.ThirdLogin;
import com.dlshouwen.swda.bms.enums.LoginOperationEnum;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.vo.*;
import com.dlshouwen.swda.core.cache.TokenCache;
import com.dlshouwen.swda.core.dict.CallResult;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.utils.Sm2Utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 权限认证服务
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {
    private final SysCaptchaService sysCaptchaService;
    private final TokenCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;
    private final SysLogLoginService sysLogLoginService;
    private final SysUserService sysUserService;
    private final SysUserTokenService sysUserTokenService;
    private final SmsApi smsApi;

    @Override
    public SysUserTokenVO loginByAccount(SysAccountLoginVO login) {
        // 验证码效验
        boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            // 保存登录日志
            sysLogLoginService.save(login.getUsername(), CallResult.FAILURE, LoginOperationEnum.CAPTCHA_FAIL.getValue());

            throw new SwdaException("验证码错误");
        }

        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), Sm2Utils.decrypt(login.getPassword())));
        } catch (BadCredentialsException e) {
            throw new SwdaException("用户名或密码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getUserId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public SysUserTokenVO loginByMobile(SysMobileLoginVO login) {
        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new MobileAuthenticationToken(login.getMobile(), login.getCode()));
        } catch (BadCredentialsException e) {
            throw new SwdaException("手机号或验证码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getUserId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public SysUserTokenVO loginByThird(SysThirdCallbackVO login) {
        Authentication authentication;
        try {
            // 转换对象
            ThirdLogin thirdLogin = BeanUtil.copyProperties(login, ThirdLogin.class);

            // 用户认证
            authentication = authenticationManager.authenticate(new ThirdAuthenticationToken(thirdLogin));
        } catch (BadCredentialsException e) {
            throw new SwdaException("第三方登录失败");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getUserId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public boolean sendCode(String mobile) {
        // 生成6位验证码
        String code = RandomUtil.randomNumbers(6);

        SysUserVO user = sysUserService.getByMobile(mobile);
        if (user == null) {
            throw new SwdaException("手机号未注册");
        }

        // 发送短信
        return smsApi.sendCode(mobile, "code", code);
    }

    @Override
    public AccessTokenVO getAccessToken(String refreshToken) {
        SysUserTokenVO token = sysUserTokenService.refreshToken(refreshToken);

        // 封装 AccessToken
        AccessTokenVO accessToken = new AccessTokenVO();
        accessToken.setAccessToken(token.getAccessToken());
        accessToken.setAccessTokenExpire(token.getAccessTokenExpire());

        return accessToken;
    }

    @Override
    public void logout(String accessToken) {
        // 用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);

        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);

        // Token过期
        sysUserTokenService.expireToken(user.getUserId());

        // 保存登录日志
        sysLogLoginService.save(user.getUsername(), CallResult.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
    }
}
