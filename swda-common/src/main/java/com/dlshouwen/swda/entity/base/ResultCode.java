package com.dlshouwen.swda.entity.base;

/**
 * result code
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ResultCode {

	/** base **/
	public static final int SUCCESS = 0;
	public static final int ERROR = -1;

	/** login error **/
	public static final int USER_ACCOUNT_ERROR = 10001;
	public static final int USER_ACCOUNT_DISABLED = 10002;
	public static final int VALID_CODE_NOT_FOUND_ERROR = 10003;
	public static final int VALID_CODE_EXPIRED_ERROR = 10004;
	public static final int VALID_CODE_ERROR = 10005;

	/** permission **/
	public static final int PERMISSION_NO_ACCESS = 90001;
	
	/** auto **/
	public static final int AUTHENTICATION_TIMEOUT = 90002;
	
	/** Token **/
	public static final int TOKEN_INVALID = 90003;

}
