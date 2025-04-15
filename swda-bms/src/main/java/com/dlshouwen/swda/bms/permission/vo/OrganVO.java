package com.dlshouwen.swda.bms.permission.vo;

import com.dlshouwen.swda.core.common.entity.TreeNode;
import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * organ vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "organ")
public class OrganVO extends TreeNode<OrganVO> {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "organ id")
	private Long organId;
	
	@Schema(description = "pre organ id")
	private Long preOrganId;
	
	@Schema(description = "pre organ name")
	private String preOrganName;
	
	@Schema(description = "organ code")
	private String organCode;
	
	@Schema(description = "organ name")
	@NotBlank(message = "机构名称不能为空")
	private String organName;
	
	@Schema(description = "contact")
	private String contact;
	
	@Schema(description = "contact phone")
	private String contactPhone;
	
	@Schema(description = "longitude")
	private Double longitude;
	
	@Schema(description = "latitude")
	private Double latitude;
	
	@Schema(description = "province id")
	private Integer provinceId;
	
	@Schema(description = "city id")
	private Integer cityId;
	
	@Schema(description = "county id")
	private Integer countyId;
	
	@Schema(description = "address")
	private String address;
	
	@Schema(description = "assist search")
	private String assistSearch;

	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;
	
	@Schema(description = "remark")
	private String remark;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}