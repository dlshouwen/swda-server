package com.dlshouwen.swda.core.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * tree node
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class TreeNode<T> implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	/** id */
	@Schema(description = "id")
	private Long id;
	
	/** pid */
	@Schema(description = "parent id")
	private Long pid;
	
	/** name */
	@Schema(description = "name")
	private String name;
	
	/** children */
	private List<T> children = new ArrayList<>();

}