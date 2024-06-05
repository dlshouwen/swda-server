package com.dlshouwen.swda.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dlshouwen.swda.entity.auth.SecurityUser;
import com.dlshouwen.swda.entity.auth.UserDetail;

/**
 * field meta object handler
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class FieldMetaObjectHandler implements MetaObjectHandler {
	
	private final static String CREATE_TIME = "createTime";
	private final static String CREATOR = "creator";
	private final static String UPDATE_TIME = "updateTime";
	private final static String UPDATER = "updater";
	private final static String VERSION = "version";
	private final static String DELETED = "deleted";

	@Override
	public void insertFill(MetaObject metaObject) {
		UserDetail user = SecurityUser.getUser();
		LocalDateTime now = LocalDateTime.now();
		// 用户字段填充
		if (user != null) {
			// 创建者
			setFieldValByName(CREATOR, user.getUserId(), metaObject);
			// 更新者
			setFieldValByName(UPDATER, user.getUserId(), metaObject);
		}
		// 创建时间
		setFieldValByName(CREATE_TIME, now, metaObject);
		// 更新时间
		setFieldValByName(UPDATE_TIME, now, metaObject);
		// 版本号
		setFieldValByName(VERSION, 0, metaObject);
		// 删除标识
		setFieldValByName(DELETED, 0, metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// 更新者
		setFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
		// 更新时间
		setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
	}

}
