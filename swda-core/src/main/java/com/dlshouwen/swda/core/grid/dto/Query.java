package com.dlshouwen.swda.core.grid.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * pager
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class Query<T> {
	
	/** page */
	private Page page;

	/** parameters */
	private Map<String, Object> parameters;

	/** manual query parameters */
	private Map<String, Object> manualQueryParameters;

	/** fast query parameters */
	private Map<String, Object> fastQueryParameters;

	/** advance query conditions */
	private List<Condition> advanceQueryConditions;

	/** advance query sorts */
	private List<Sort> advanceQuerySorts;

}
