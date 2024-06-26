package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户查询")
public class SysUserQuery extends Query {
	@Schema(description = "用户名")
	private String username;

	@Schema(description = "手机号")
	private String mobile;

	@Schema(description = "性别")
	private Integer gender;

	@Schema(description = "机构ID")
	private Long orgId;

}
