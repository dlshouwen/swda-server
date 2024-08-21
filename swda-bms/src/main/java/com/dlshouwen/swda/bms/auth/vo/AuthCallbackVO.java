package com.dlshouwen.swda.bms.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * auth callback vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "auth callback")
public class AuthCallbackVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "auth platform type")
	private Integer authPlatformType;

	@Schema(description = "code")
	private String code;

	@Schema(description = "state")
	private String state;

}
