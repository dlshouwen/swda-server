package com.dlshouwen.swda.core.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * auth callback vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "auth callback")
public class AuthCallbackVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "open type")
	private String openType;

	@Schema(description = "code")
	private String code;

	@Schema(description = "state")
	private String state;

}
