package com.dlshouwen.swda.core.grid.dto;

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
	private long total;

	@Schema(description = "datas")
	private List<T> datas;

	/**
	 * constructor
	 * @param datas
	 * @param total
	 */
	public PageResult(List<T> datas, long total) {
//		set datas
		this.datas = datas;
//		set total
		this.total = total;
	}

}