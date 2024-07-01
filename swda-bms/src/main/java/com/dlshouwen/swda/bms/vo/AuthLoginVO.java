package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * auth login vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "auth login")
public class AuthLoginVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "open type")
	private String openType;

	@Schema(description = "open id")
	private String openId;

	@Schema(description = "username")
	private String username;

	@Schema(description = "user id")
	private Long userId;

	@Schema(description = "tenant id")
	private Long tenantId;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}