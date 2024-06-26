package com.dlshouwen.swda.bms.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * role user query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "role user query")
public class SysRoleUserQuery extends SysUserQuery {

	@Schema(description = "role id")
	private Long roleId;

}
