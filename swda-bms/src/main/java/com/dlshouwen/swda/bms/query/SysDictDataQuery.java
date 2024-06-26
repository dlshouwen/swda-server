package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * dict data query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "dict data query")
public class SysDictDataQuery extends Query {

	@Schema(description = "dict type id")
	@NotNull(message = "字典类型ID不能为空")
	private Long dictTypeId;

}
