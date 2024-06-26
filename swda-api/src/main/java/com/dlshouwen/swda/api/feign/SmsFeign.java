package com.dlshouwen.swda.api.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * sms feign
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@FeignClient(name = "", contextId = "sms")
public interface SmsFeign {

	/**
	 * send
	 * @param mobile
	 * @param params
	 * @return is send success
	 */
	@PostMapping(value = "api/message/sms/send")
	Boolean send(@RequestParam("mobile") String mobile, @RequestParam("params") Map<String, String> params);

	/**
	 * send code
	 * @param mobile
	 * @param key
	 * @param value
	 * @return is send success
	 */
	@PostMapping(value = "api/message/sms/sendCode")
	Boolean sendCode(@RequestParam("mobile") String mobile, @RequestParam("key") String key, @RequestParam("value") String value);

}
