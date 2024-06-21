package com.dlshouwen.swda.core.entity.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * page result
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "page result")
public class PageResult<T> implements Serializable {
	
	/** serval version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "total")
	private int total;

	@Schema(description = "list")
	private List<T> list;

	/**
	 * constructor
	 * @param list
	 * @param total
	 */
	public PageResult(List<T> list, long total) {
		this.list = list;
		this.total = (int) total;
	}

}