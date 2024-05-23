package com.dlshouwen.swda.entity.base;

import lombok.Data;

/**
 * login user
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class LoginUser {

	private Long logId;

	private Long userId;
	
	private String userCode;

	private String userName;

	private Long organId;

	private String organName;
	
	private String password;
	
	private String key;
	
	private String validCode;

}
