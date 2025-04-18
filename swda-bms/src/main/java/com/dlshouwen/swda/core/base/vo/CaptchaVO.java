package com.dlshouwen.swda.core.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * captcha vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "captcha")
public class CaptchaVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "key")
	private String key;

	@Schema(description = "image base64")
	private String image;

}
