package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.entity.base.TreeNode;
import com.dlshouwen.swda.core.utils.DateUtils;
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
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "menu")
public class SysMenuVO extends TreeNode<SysMenuVO> {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "menu name")
	@NotBlank(message = "菜单名称不能为空")
	private String name;

	@Schema(description = "menu url")
	private String url;

	@Schema(description = "menu type")
	@Range(min = 0, max = 2, message = "类型不正确")
	private Integer type;

	@Schema(description = "open style")
	@Range(min = 0, max = 1, message = "打开方式不正确")
	private Integer openStyle;

	@Schema(description = "icon")
	private String icon;

	@Schema(description = "authority")
	private String authority;

	@Schema(description = "sort")
	@Min(value = 0, message = "排序值不能小于0")
	private Integer sort;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	@Schema(description = "parent name")
	private String parentName;

}