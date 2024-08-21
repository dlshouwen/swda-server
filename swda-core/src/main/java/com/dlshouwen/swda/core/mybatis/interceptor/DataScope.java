package com.dlshouwen.swda.core.mybatis.interceptor;

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
