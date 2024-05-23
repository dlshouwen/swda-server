package com.dlshouwen.swda.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.dlshouwen.swda.config.SwdaConfig;
import com.dlshouwen.swda.entity.base.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * token
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class TokenUtils {

	//	logger
	private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	/**
	 * create token
	 * @param loginUser
	 * @return Token值
	 */
	public static String createToken(LoginUser loginUser) {
//		login user to json
		String loginUserJSON = null;
		try {
			loginUserJSON = ObjectMapperUtils.getInstance().writeValueAsString(loginUser);
		} catch (JsonProcessingException e) {
			logger.error("error", e);
		}
//		get now / expire time
		DateTime now = DateTime.now();
		DateTime expireTime = now.offsetNew(DateField.MILLISECOND, 24);
//		set payload
		Map<String, Object> payload = new HashMap<>();
		payload.put(JWTPayload.ISSUED_AT, now);
		payload.put(JWTPayload.EXPIRES_AT, expireTime);
		payload.put(JWTPayload.NOT_BEFORE, now);
		payload.put("data", loginUserJSON);
//		return token
		return JWTUtil.createToken(payload, SwdaConfig.TOKEN_SIGNKEY.getBytes());
	}

	/**
	 * get login user
	 * @return login user
	 */
	public static LoginUser getLoginUser() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(attributes!=null)
			return getLoginUser(attributes.getRequest().getHeader("token"));
		return null;
	}

	/**
	 * get login user
	 * @param request
	 * @return login user
	 */
	public static LoginUser getLoginUser(HttpServletRequest request) {
		return getLoginUser(request.getHeader("token"));
	}

	/**
	 * get login user
	 * @param token
	 * @return login user
	 */
	public static LoginUser getLoginUser(String token) {
//		get jwt
		JWT jwt = JWTUtil.parseToken(token).setKey(SwdaConfig.TOKEN_SIGNKEY.getBytes());
//		get payloads
		JSONObject payloads = jwt.getPayloads();
//		get login user json
		String loginUserJSON = payloads.getStr("data");
//		if null
		if(StringUtils.isEmpty(loginUserJSON))
			return null;
//		set login user
		LoginUser loginUser = null;
		try {
			loginUser = ObjectMapperUtils.getInstance().readValue(loginUserJSON, LoginUser.class);
		} catch (JsonProcessingException e) {
			logger.error("error", e);
		}
//		return
		return loginUser;
	}

	/**
	 * remove token
	 * @param token
	 */
	public static void removeToken(String token) {
	}

}
