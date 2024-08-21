package com.dlshouwen.swda.core.grid.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * export
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class Export<T> {

	/** type: excel, pdf, txt, cvs */
	private String type;

	/** title */
	private String title;

	/** columns */
	private List<Column> columns;

	/** all */
	private boolean all;

	/** processed */
	private boolean processed;

	/** datas */
	private List<Map<String, Object>> datas;

}
