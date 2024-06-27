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
 * user token vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@Schema(description = "user token")
public class SysUserTokenVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "access token")
	@JsonProperty(value = "access_token")
	private String accessToken;

	@Schema(description = "refresh token")
	@JsonProperty(value = "refresh_token")
	private String refreshToken;

	@Schema(description = "access token expire")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime accessTokenExpire;

	@Schema(description = "refresh token expire")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime refreshTokenExpire;

}
