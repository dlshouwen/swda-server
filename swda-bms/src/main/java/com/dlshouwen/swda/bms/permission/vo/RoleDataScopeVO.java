package com.dlshouwen.swda.bms.permission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * role data scope vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "role data scope")
public class RoleDataScopeVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "role id")
	@NotNull(message = "角色ID不能为空")
	private Long roleId;

	@Schema(description = "data scope")
	@NotNull(message = "数据范围不能为空")
	private String dataScope;

	@Schema(description = "organ id list")
	private List<Long> organIdList;

}