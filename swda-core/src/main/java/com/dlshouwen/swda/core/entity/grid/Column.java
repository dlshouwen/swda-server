package com.dlshouwen.swda.core.entity.grid;

import lombok.Data;

import java.util.Map;

/**
 * column
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class Column {

	/** id */
	private String id;

	/** export */
	private boolean export = true;

	/** select export */
	private boolean selectExport = true;

	/** print */
	private boolean print = true;

	/** extra */
	private boolean extra = true;

	/** title */
	private String title;

	/** width */
	private String width;

	/** type */
	private String type;

	/** format */
	private String format;

	/** otype */
	private String otype;

	/** oformat */
	private String oformat;

	/** dict */
	private Map<String, Object> dict;

	/** column style */
	private String columnStyle;

	/** column class */
	private String columnClass;

	/** header style */
	private String headerStyle;

	/** header class */
	private String headerClass;

	/** hide */
	private boolean hide = false;

	/** hide type */
	private String hideType;

	/** fast query */
	private String fast;

	/** advance query */
	private boolean advance;

	/** resolution */
	private String resolution;

}
