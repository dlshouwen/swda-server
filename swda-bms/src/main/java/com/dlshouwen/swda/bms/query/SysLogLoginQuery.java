package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * login log query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "login log query")
public class SysLogLoginQuery extends Query {

	@Schema(description = "username")
	private String username;

	@Schema(description = "address")
	private String address;

	@Schema(description = "status")
	private Integer status;

}