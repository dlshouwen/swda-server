package com.dlshouwen.swda.api.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 短信服务
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@FeignClient(name="", contextId = "sms")
public interface SmsFeign {

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param params 参数
     * @return 是否发送成功
     */
    @PostMapping(value = "api/message/sms/send")
    Boolean send(@RequestParam("mobile") String mobile, @RequestParam("params") Map<String, String> params);

    /**
     * 发送短信
     * @param mobile 手机号
     * @param key    参数KEY
     * @param value  参数Value
     * @return 是否发送成功
     */
    @PostMapping(value = "api/message/sms/sendCode")
    Boolean sendCode(@RequestParam("mobile") String mobile, @RequestParam("key") String key, @RequestParam("value") String value);

}
