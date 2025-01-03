package com.dlshouwen.swda.bms.system.vo;

import com.dlshouwen.swda.core.common.entity.TreeNode;
import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

/**
 * permission vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "permission")
public class PermissionVO extends TreeNode<PermissionVO> {

	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "permission id")
	private Long permissionId;
	
	@Schema(description = "parent permission id")
	private Long prePermissionId;

	@Schema(description = "permission code")
	@NotBlank(message = "权限编码不能为空")
	private String permissionCode;

	@Schema(description = "permission name")
	@NotBlank(message = "权限名称不能为空")
	private String permissionName;

	@Schema(description = "permission type")
	@Range(min = 0, max = 2, message = "类型不正确")
	private Integer permissionType;

	@Schema(description = "authority")
	private String authority;

	@Schema(description = "path")
	private String path;

	@Schema(description = "component")
	private String component;

	@Schema(description = "icon")
	private String icon;

	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	@Schema(description = "parent permission name")
	private String prePermissionName;

}