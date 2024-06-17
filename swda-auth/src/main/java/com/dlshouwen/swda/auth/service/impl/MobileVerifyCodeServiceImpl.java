package com.dlshouwen.swda.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.api.module.SmsApi;
import com.dlshouwen.swda.auth.service.MobileVerifyCodeService;

/**
 * 短信验证码效验
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Service
@AllArgsConstructor
public class MobileVerifyCodeServiceImpl implements MobileVerifyCodeService {
    private final SmsApi smsApi;

    @Override
    public boolean verifyCode(String mobile, String code) {
        return smsApi.verifyCode(mobile, code);
    }
}
