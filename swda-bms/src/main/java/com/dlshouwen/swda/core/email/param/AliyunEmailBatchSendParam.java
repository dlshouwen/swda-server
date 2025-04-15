package com.dlshouwen.swda.common.email.param;

import lombok.Data;

/**
 * aliyun email batch send param
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class AliyunEmailBatchSendParam {
	
	/** group name */
	private String groupName;
	
	/** from */
	private String from;
	
	/** receivers name */
	private String receiversName;
	
	/** template name */
	private String templateName;
	
	/** tag name */
	private String tagName;

}
