package com.dlshouwen.swda.common.email.param;

import lombok.Data;

/**
 * aliyun email send param
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class AliyunEmailSendParam {
	
	/** group name */
	private String groupName;
	
	/** from */
	private String from;
	
	/** from alias */
	private String formAlias;
	
	/** tos, split by ',' */
	private String tos;
	
	/** subject */
	private String subject;
	
	/** content */
	private String content;
	
	/** html */
	private boolean html;

}
