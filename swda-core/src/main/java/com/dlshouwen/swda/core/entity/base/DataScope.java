package com.dlshouwen.swda.core.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * data scope
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class DataScope {

	/** sql filter */
	private String sqlFilter;

}
