package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户Token
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@Schema(description = "用户Token")
public class SysUserTokenVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "access_token")
	@JsonProperty(value = "access_token")
	private String accessToken;

	@Schema(description = "refresh_token")
	@JsonProperty(value = "refresh_token")
	private String refreshToken;

	@Schema(description = "access_token 过期时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime accessTokenExpire;

	@Schema(description = "refresh_token 过期时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime refreshTokenExpire;
}
