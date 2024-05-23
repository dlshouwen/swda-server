package com.dlshouwen.swda.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dlshouwen.swda.entity.base.Data;
import com.dlshouwen.swda.entity.base.LoginUser;

import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * task utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class TaskUtils {

	//	logger
	private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);

	/**
	 * get task sql by template
	 * @param sql
	 * @return sql
	 */
	public static String getTaskSqlByTemplate(String sql) {
//		get login user
		LoginUser loginUser = TokenUtils.getLoginUser();
//		defined result sql
		String resultSql = sql;
//		find su.*
		Matcher matcher = Pattern.compile("\\$\\{su\\.[\\S&&[^}]]+}").matcher(sql);
		while(matcher.find()){
//			get attr / attr name
			String attr = matcher.group();
			String attrName = attr.replace("${su.", "").replace("}", "").trim();
//			get info from login user
			Method method = null;
			try {
				method = LoginUser.class.getMethod("get"+attrName.toUpperCase().charAt(0)+attrName.substring(1));
			} catch (NoSuchMethodException e) {
				logger.error("error", e);
			}
			String value = null;
			try {
				value = method==null||method.invoke(loginUser)==null?"":method.invoke(loginUser).toString();
			} catch (IllegalAccessException | InvocationTargetException e) {
				logger.error("error", e);
			}
//			replace su info
			resultSql = resultSql.replaceAll("\\$\\{su\\."+attrName+"}", value==null?"":value);
		}
//		find sw.attr.*
		matcher = Pattern.compile("\\$\\{sw\\.attr\\.[\\S&&[^}]]+}").matcher(sql);
		while(matcher.find()){
//			get attr / attr name
			String attr = matcher.group();
			String attrName = attr.replace("${sw.attr.", "").replace("}", "").trim();
//			replace attr info
			resultSql = resultSql.replaceAll("\\$\\{sw\\.attr\\."+attrName+"}", Data.attr.get(attrName));
		}
//		find and replace cxp
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(attributes!=null){
			HttpServletRequest request = attributes.getRequest();
			resultSql = resultSql.replaceAll("\\$\\{cxp[\\s]*}", request.getContextPath());
		}
//		find and replace version
		resultSql = resultSql.replaceAll("\\$\\{version[\\s]*}", MapUtil.getStr(Data.attr, "version"));
//		return
		return resultSql;
	}
	
	/**
	 * get message by template
	 * @param message
	 * @param result
	 * @return message
	 */
	public static String getMessageByTemplate(String message, int result) {
//		get login user
		LoginUser loginUser = TokenUtils.getLoginUser();
//		defined result message
		String resultMessage = message;
//		find su.*
		Matcher matcher = Pattern.compile("\\$\\{su\\.[\\S&&[^}]]+}").matcher(message);
		while(matcher.find()){
//			get attr / attr name
			String attr = matcher.group();
			String attrName = attr.replace("${su.", "").replace("}", "").trim();
//			get info from login user
			Method method = null;
			try {
				method = LoginUser.class.getMethod("get"+attrName.toUpperCase().charAt(0)+attrName.substring(1));
			} catch (NoSuchMethodException e) {
				logger.error("error", e);
			}
			String value = null;
			try {
				value = method==null?"":(String)method.invoke(loginUser);
			} catch (IllegalAccessException | InvocationTargetException e) {
				logger.error("error", e);
			}
//			replace su info
			resultMessage = resultMessage.replaceAll("\\$\\{su\\."+attrName+"}", value==null?"":value);
		}
//		find sw.attr.*
		matcher = Pattern.compile("\\$\\{sw\\.attr\\.[\\S&&[^}]]+}").matcher(resultMessage);
		while(matcher.find()){
//			get attr / attr name
			String attr = matcher.group();
			String attrName = attr.replace("${sw.attr.", "").replace("}", "").trim();
//			replace attr info
			resultMessage = resultMessage.replaceAll("\\$\\{sw\\.attr\\."+attrName+"}", Data.attr.get(attrName));
		}
//		find and replace cxp
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(attributes!=null) {
			HttpServletRequest request = attributes.getRequest();
			resultMessage = resultMessage.replaceAll("\\$\\{cxp[\\s]*}", request.getContextPath());
		}
//		find and replace version
		resultMessage = resultMessage.replaceAll("\\$\\{version[\\s]*}", MapUtil.getStr(Data.attr, "version"));
//		find and replace result
		resultMessage = resultMessage.replaceAll("\\$\\{result[\\s]*}", String.valueOf(result));
//		return
		return resultMessage;
	}
	
}
