package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "user query")
public class SysUserQuery extends Query {
	
	@Schema(description = "username")
	private String username;

	@Schema(description = "mobile")
	private String mobile;

	@Schema(description = "gender")
	private Integer gender;

	@Schema(description = "organ id")
	private Long orgId;

}
