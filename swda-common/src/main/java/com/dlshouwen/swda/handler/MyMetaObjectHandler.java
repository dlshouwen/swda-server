package com.dlshouwen.swda.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dlshouwen.swda.entity.base.LoginUser;
import com.dlshouwen.swda.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * my meta object handler
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	/**
	 * insert fill
	 * @param metaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
//		set create time / edit time
		this.setFieldValByName("create_time", new Date(), metaObject);
		this.setFieldValByName("edit_time", new Date(), metaObject);
//		get creator / editor field
		Object creator = this.getFieldValByName("creator", metaObject);
		Object editor = this.getFieldValByName("editor", metaObject);
//		defined operator
		Long operator = -2l;
//		get request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		if request not null
		if(request!=null){
//			get login user
			LoginUser loginUser = TokenUtils.getLoginUser(request);
//			if login user not null
			if(loginUser!=null){
//				set operator
				operator = loginUser.getUserId();
			}
		}
//		set creator / editor
		if(creator!=null) this.setFieldValByName("creator", operator, metaObject);
		if(editor!=null) this.setFieldValByName("editor", operator, metaObject);
	}

	/**
	 * update fill
	 * @param metaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
//		set edit time
		this.setFieldValByName("edit_time", new Date(), metaObject);
//		get editor field
		Object editor = this.getFieldValByName("editor", metaObject);
//		defined operator
		Long operator = -2l;
//		get request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		if request not null
		if(request!=null){
//			get login user
			LoginUser loginUser = TokenUtils.getLoginUser(request);
//			if login user not null
			if(loginUser!=null){
//				set operator
				operator = loginUser.getUserId();
			}
		}
//		set editor
		if(editor!=null) this.setFieldValByName("editor", operator, metaObject);
	}

}