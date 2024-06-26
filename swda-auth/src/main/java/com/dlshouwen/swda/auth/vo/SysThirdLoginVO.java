package com.dlshouwen.swda.auth.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * third login vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "third login")
public class SysThirdLoginVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "开放平台类型")
	private String openType;

	@Schema(description = "开放平台，唯一标识")
	private String openId;

	@Schema(description = "昵称")
	private String username;

	@Schema(description = "用户ID")
	private Long userId;

	@Schema(description = "租户ID")
	private Long tenantId;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}