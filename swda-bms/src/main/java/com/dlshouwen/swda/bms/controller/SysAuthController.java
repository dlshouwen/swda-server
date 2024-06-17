package com.dlshouwen.swda.bms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.bms.service.SysAuthService;
import com.dlshouwen.swda.bms.service.SysCaptchaService;
import com.dlshouwen.swda.bms.vo.*;
import com.dlshouwen.swda.core.entity.base.R;
import com.dlshouwen.swda.core.utils.TokenUtils;

import org.springframework.web.bind.annotation.*;

/**
 * 认证管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@RestController
@RequestMapping("sys/auth")
@Tag(name = "认证管理")
@AllArgsConstructor
public class SysAuthController {
    private final SysCaptchaService sysCaptchaService;
    private final SysAuthService sysAuthService;

    @GetMapping("captcha")
    @Operation(summary = "验证码")
    public R<SysCaptchaVO> captcha() {
        SysCaptchaVO captchaVO = sysCaptchaService.generate();

        return R.ok(captchaVO);
    }

    @GetMapping("captcha/enabled")
    @Operation(summary = "是否开启验证码")
    public R<Boolean> captchaEnabled() {
        boolean enabled = sysCaptchaService.isCaptchaEnabled();

        return R.ok(enabled);
    }

    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    public R<SysUserTokenVO> login(@RequestBody SysAccountLoginVO login) {
        SysUserTokenVO token = sysAuthService.loginByAccount(login);

        return R.ok(token);
    }

    @PostMapping("send/code")
    @Operation(summary = "发送短信验证码")
    public R<String> sendCode(String mobile) {
        boolean flag = sysAuthService.sendCode(mobile);
        if (!flag) {
            return R.error("短信发送失败！");
        }

        return R.ok();
    }

    @PostMapping("mobile")
    @Operation(summary = "手机号登录")
    public R<SysUserTokenVO> mobile(@RequestBody SysMobileLoginVO login) {
        SysUserTokenVO token = sysAuthService.loginByMobile(login);

        return R.ok(token);
    }

    @PostMapping("third")
    @Operation(summary = "第三方登录")
    public R<SysUserTokenVO> third(@RequestBody SysThirdCallbackVO login) {
        SysUserTokenVO token = sysAuthService.loginByThird(login);

        return R.ok(token);
    }

    @PostMapping("token")
    @Operation(summary = "获取 accessToken")
    public R<AccessTokenVO> token(String refreshToken) {
        AccessTokenVO token = sysAuthService.getAccessToken(refreshToken);

        return R.ok(token);
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public R<String> logout(HttpServletRequest request) {
        sysAuthService.logout(TokenUtils.getAccessToken(request));

        return R.ok();
    }
}
