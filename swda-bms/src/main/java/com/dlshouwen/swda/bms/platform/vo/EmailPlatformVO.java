package com.dlshouwen.swda.bms.platform.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

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
	private String emailPlatformType;

	@Schema(description = "status")
	private String status;

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

	@Schema(description = "end point")
	private String endPoint;

	@Schema(description = "access key")
	private String accessKey;

	@Schema(description = "secret key")
	private String secretKey;

	@Schema(description = "assist search")
	@Length(min = 0, max = 400, message = "辅助查询长度在 0-400 之间")
	private String assistSearch;
	
	@Schema(description = "sort")
	@Range(min = 0, message = "排序数据必须大于 0")
	private Integer sort;

	@Schema(description = "remark")
	@Length(min = 0, max = 200, message = "备注长度在 0-200 之间")
	private String remark;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

}