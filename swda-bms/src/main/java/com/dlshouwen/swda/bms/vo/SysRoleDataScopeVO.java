package com.dlshouwen.swda.bms.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * role data scope vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "role data scope")
public class SysRoleDataScopeVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	@NotNull(message = "角色ID不能为空")
	private Long id;

	@Schema(description = "data scope 0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据")
	@NotNull(message = "数据范围不能为空")
	private Integer dataScope;

	@Schema(description = "organ id list")
	private List<Long> orgIdList;

}