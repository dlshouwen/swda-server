package com.dlshouwen.swda.bms.vo;

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
public class DataLogVO implements Serializable {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "log id")
	private Long logId;

	@Schema(description = "execute type")
	private Integer executeType;

	@Schema(description = "execute sql")
	private String executeSql;

	@Schema(description = "execute method")
	private String executeMethod;

	@Schema(description = "execute params")
	private String executeParams;

	@Schema(description = "execute result class")
	private String executeResultClass;

	@Schema(description = "execute result")
	private String executeResult;

	@Schema(description = "call type")
	private int callType;

	@Schema(description = "call source")
	private String callSource;

	@Schema(description = "line no")
	private int lineNo;

	@Schema(description = "call result")
	private Integer callResult;

	@Schema(description = "error reason")
	private String errorReason;

	@Schema(description = "start time")
	private LocalDateTime startTime;

	@Schema(description = "end time")
	private LocalDateTime endTime;

	@Schema(description = "cost")
	private int cost;

	@Schema(description = "tenant id")
	private Long tenantId;

	@Schema(description = "user id")
	private Long userId;

	@Schema(description = "real name")
	private String realName;

	@Schema(description = "organ id")
	private Long organId;

	@Schema(description = "organ name")
	private String organName;

	@Schema(description = "ip")
	private String ip;

}