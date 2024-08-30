package com.dlshouwen.swda.bms.security.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * account login vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "account login")
public class UserLoginVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "username")
	private String username;

	@Schema(description = "password")
	private String password;

	@Schema(description = "key")
	private String key;

	@Schema(description = "captcha")
	private String captcha;

}
