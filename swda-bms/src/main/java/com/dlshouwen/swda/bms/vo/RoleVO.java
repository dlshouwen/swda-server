package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * role vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "role")
public class RoleVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "role name")
	@NotBlank(message = "角色名称不能为空")
	private String name;

	@Schema(description = "role code")
	@NotBlank(message = "角色编码不能为空")
	private String roleCode;

	@Schema(description = "remark")
	private String remark;

	@Schema(description = "data scope 0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据")
	private Integer dataScope;

	@Schema(description = "menu id list")
	private List<Long> menuIdList;

	@Schema(description = "organ id list")
	private List<Long> orgIdList;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}