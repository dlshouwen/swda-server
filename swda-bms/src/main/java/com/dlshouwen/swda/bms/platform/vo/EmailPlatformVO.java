package com.dlshouwen.swda.bms.platform.vo;

import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * email platform vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "email platform")
public class EmailPlatformVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "email platform id")
	private Long emailPlatformId;
	
	@Schema(description = "email platform code")
	private String emailPlatformCode;
	
	@Schema(description = "email platform name")
	private String emailPlatformName;

	@Schema(description = "email platform type")
	private Integer emailPlatformType;

	@Schema(description = "status")
	private Integer status;

	@Schema(description = "group name")
	private String groupName;

	@Schema(description = "mail host")
	private String mailHost;

	@Schema(description = "mail port")
	private Integer mailPort;

	@Schema(description = "mail from")
	private String mailFrom;

	@Schema(description = "mail pass")
	private String mailPass;

	@Schema(description = "region id")
	private String regionId;

	@Schema(description = "endpoint")
	private String endpoint;

	@Schema(description = "access key")
	private String accessKey;

	@Schema(description = "secret key")
	private String secretKey;

	@Schema(description = "version")
	private Integer version;

	@Schema(description = "creator")
	private Long creator;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

	@Schema(description = "updater")
	private Long updater;

	@Schema(description = "update time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date updateTime;

	@Schema(description = "deleted")
	private Integer deleted;

}