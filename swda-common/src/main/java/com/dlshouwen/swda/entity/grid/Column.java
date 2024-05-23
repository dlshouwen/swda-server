package com.dlshouwen.swda.entity.grid;

import lombok.Data;

import java.util.Map;

/**
 * column
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class Column {
	
	/** index */
	private int _index_;
	
	/** id */
	private String id;

	/** label */
	private String label;
	
	/** width */
	private String width;
	
	/** min */
	private int min;
	
	/** max */
	private int max;
	
	/** type */
	private String type;
	
	/** dict */
	private Map<String, Object> dict;
	
	/** format */
	private String format;
	
	/** otype */
	private String otype;
	
	/** oformat */
	private String oformat;
	
	/** encode */
	private boolean encode;
	
	/** decode */
	private boolean decode;
	
	/** style */
	private String style;
	
	/** _class */
	private String _class;
	
	/** hstyle */
	private String hstyle;
	
	/** hclass */
	private String hclass;
	
	/** align */
	private String align;
	
	/** halign */
	private String halign;
	
	/** hide */
	private boolean hide;
	
	/** merge */
	private boolean merge;
	
	/** frozen */
	private String frozen;
	
	/** resize */
	private boolean resize;
	
	/** move */
	private boolean move;
	
	/** sort */
	private boolean sort;

	/** fast */
	private String fast;
	
	/** ftype */
	private String ftype;
	
	/** advance */
	private boolean advance;

	/** export */
	private boolean export;

	/** print */
	private boolean print = true;
	
	/** resolution */
	private String resolution;

}
