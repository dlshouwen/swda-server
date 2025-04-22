package com.dlshouwen.swda.core.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * tree node
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class TreeNode<T> implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	/** id */
	private Object id;
	
	/** pid */
	private Object pid;
	
	/** name */
	private String name;
	
	/** label */
	private String label;
	
	/** value */
	private Object value;
	
	/** children */
	private List<T> children = new ArrayList<>();

}