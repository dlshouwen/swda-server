package com.dlshouwen.swda.auth.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * third login config vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "third login config")
public class SysThirdLoginConfigVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "开放平台类型")
	private String openType;

	@Schema(description = "ClientID")
	private String clientId;

	@Schema(description = "ClientSecret")
	private String clientSecret;

	@Schema(description = "RedirectUri")
	private String redirectUri;

	@Schema(description = "AgentID")
	private String agentId;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}