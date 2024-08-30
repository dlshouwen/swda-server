package com.dlshouwen.swda.core.mybatis.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.user.UserDetail;

/**
 * field meta object handler
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class FieldMetaObjectHandler implements MetaObjectHandler {

	/** const */
	private final static String CREATE_TIME = "createTime";
	private final static String CREATOR = "creator";
	private final static String UPDATE_TIME = "updateTime";
	private final static String UPDATER = "updater";
	private final static String VERSION = "version";
	private final static String DELETED = "deleted";

	/**
	 * insert fill
	 * @param meta object
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
//		get login user
		UserDetail user = SecurityUser.getUser();
//		get now
		LocalDateTime now = LocalDateTime.now();
//		if has user
		if (user != null) {
//			set creator, updater
			setFieldValByName(CREATOR, user.getUserId(), metaObject);
			setFieldValByName(UPDATER, user.getUserId(), metaObject);
		}
//		set create time, update time
		setFieldValByName(CREATE_TIME, now, metaObject);
		setFieldValByName(UPDATE_TIME, now, metaObject);
//		set version, deleted
		setFieldValByName(VERSION, 0, metaObject);
		setFieldValByName(DELETED, 0, metaObject);
	}

	/**
	 * update fill
	 * @param meta object
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
//		set updater, update time
		setFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
		setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
	}
	
}
