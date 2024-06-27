package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * access token vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "access token")
public class AccessTokenVO implements Serializable {

	/** serial version uid */
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "access token")
	@JsonProperty(value = "access_token")
	private String accessToken;

	@Schema(description = "access token expire")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime accessTokenExpire;

}
