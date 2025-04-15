package com.dlshouwen.swda.core.email.param;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * local email send param
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class LocalEmailSendParam {
	
	/** group name */
	private String groupName;
	
	/** tos, split with ',' */
	private String tos;
	
	/** subject */
	private String subject;
	
	/** content */
	private String content;
	
	/** html */
	private boolean html;
	
	/** files */
	private List<File> files = CollectionUtil.newArrayList();

}
