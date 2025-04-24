package com.dlshouwen.swda.bms.system.vo;

import com.dlshouwen.swda.core.base.entity.TreeNode;
import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

/**
 * menu vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "menu")
public class MenuVO extends TreeNode<MenuVO> {

	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "menu id")
	private Long menuId;
	
	@Schema(description = "parent menu id")
	private Long preMenuId;
	
	@Schema(description = "system id")
	private Long systemId;
	
	@Schema(description = "menu code")
	@NotBlank(message = "菜单编码不能为空")
	private String menuCode;

	@Schema(description = "menu name")
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;

	@Schema(description = "menu type")
	@Range(min = 0, max = 2, message = "菜单类型不正确")
	private String menuType;

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

	@Schema(description = "parent menu name")
	private String preMenuName;

}