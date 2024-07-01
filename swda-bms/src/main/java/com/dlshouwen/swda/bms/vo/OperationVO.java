package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * operation log vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "operation log")
public class OperationVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "user id")
	private Long userId;

	@Schema(description = "real name")
	private String realName;

	@Schema(description = "module")
	private String module;

	@Schema(description = "name")
	private String name;

	@Schema(description = "request uri")
	private String reqUri;

	@Schema(description = "request method")
	private String reqMethod;

	@Schema(description = "request params")
	private String reqParams;

	@Schema(description = "ip")
	private String ip;

	@Schema(description = "address")
	private String address;

	@Schema(description = "user agent")
	private String userAgent;

	@Schema(description = "operate type")
	private Integer operateType;

	@Schema(description = "duration")
	private Integer duration;

	@Schema(description = "status")
	private Integer status;

	@Schema(description = "result message")
	private String resultMsg;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}