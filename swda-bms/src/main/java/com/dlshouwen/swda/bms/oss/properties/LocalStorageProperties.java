package com.dlshouwen.swda.bms.oss.properties;

import lombok.Data;

/**
 * local storage properties
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public class LocalStorageProperties {
	
	private String path;
	
	private String url = "upload";

}
