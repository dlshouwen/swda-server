package com.dlshouwen.swda.utils;

import cn.hutool.core.util.StrUtil;
import com.dlshouwen.swda.entity.base.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class SqlUtils {

//	logger
	private static final Logger logger = LoggerFactory.getLogger(SqlUtils.class);

	/**
	 * get args key
	 * @param values
	 * @param splitChar
	 * @return args key
	 */
	public static String getArgsKey(String values, String splitChar) {
//		only 1 value
		if(StrUtil.isEmpty(values)){
			return "?";
		}
//		defined and append attrs
		StringBuilder attrs = new StringBuilder();
		for(int i=0; i<values.split(splitChar).length; i++){
			attrs.append("?").append(",");
		}
//		if length is 0
		if(attrs.isEmpty()){
			return "?";
		}
//		delete last ,
		attrs.deleteCharAt(attrs.length()-1);
//		return
		return attrs.toString();
	}

	/**
	 * get args value
	 * @param values
	 * @param splitChar
	 * @return args
	 */
	public static List<Object> getArgsValue(String values, String splitChar) {
//		defined list
		List<Object> list = new ArrayList<>();
//		only 1 value
		if(StrUtil.isEmpty(values)){
			list.add("");
			return list;
		}
//		add list
		Collections.addAll(list, values.split(splitChar));
//		if size is 0
		if(list.isEmpty())
			list.add("");
//		return
		return list;
	}

	/**
	 * get args integer value
	 * @param values
	 * @param splitChar
	 * @return args
	 */
	public static List<Object> getArgsIntegerValue(String values, String splitChar) {
//		only 1 value
		if(StrUtil.isEmpty(values))
			return new ArrayList<>();
//		defined and add list
		List<Object> list = new ArrayList<>();
		for(String value : values.split(splitChar))
			list.add(Integer.parseInt(value));
		return list;
	}

	/**
	 * get object sql
	 * @param sql
	 * @return sql
	 */
	public static String getObjectSql(String sql) {
		return sql.replaceAll("\\$\\{\\s*[\\S&&[^}]]+\\s*}", "?");
	}

	/**
	 * get object args
	 * @param sql
	 * @param object
	 * @param args
	 * @return params
	 */
	public static List<Object> getObjectArgs(String sql, Object object, Object...args) {
//		find ${*}
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^}]]+\\s*}").matcher(sql);
//		defined params
		List<Object> params = new ArrayList<>();
//		get class
		Class<?> objectClass = object.getClass();
//		get property descriptors
		PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[0];
		try {
			propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("error", e);
		}
//		find attr
		int i=0;
		while(matcher.find()){
//			get attr
			String attr = StrUtil.toCamelCase(matcher.group().replace("${", "").replace("}", "").trim());
//			defined is find
			boolean isFind = false;
//			for each property descriptor
			for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
//				find
				if(attr.equalsIgnoreCase(propertyDescriptor.getName())){
//					get read method
					Method method = propertyDescriptor.getReadMethod();
//					get object value
					Object attrObject = null;
					try {
						attrObject = method.invoke(object);
					} catch (IllegalAccessException | InvocationTargetException e) {
						logger.error("error", e);
					}
//					set params
					params.add(attrObject);
//					set is fined true
					isFind = true;
//					break
					break;
				}
			}
//			if not find
			if(!isFind){
				System.out.println(attr);
//				params add from args
				params.add(args[i]);
				i++;
			}
		}
//		return
		return params;
	}

	/**
	 * get map args
	 * @param sql
	 * @param object
	 * @param args
	 * @return params
	 */
	public static List<Object> getMapArgs(String sql, Map<String, Object> object, Object...args) {
//		find ${*}
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^}]]+\\s*}").matcher(sql);
//		defined params
		List<Object> params = new ArrayList<>();
//		find attr
		int i=0;
		while(matcher.find()){
//			get attr
			String attr = matcher.group().replace("${", "").replace("}", "").trim();
//			if contains key
			if(object.containsKey(attr)){
//				add params from map
				params.add(object.get(attr));
			}else {
//				add params from args
				params.add(args[i]);
				i++;
			}
		}
//		return
		return params;
	}

	/**
	 * get entity args
	 * @param sql
	 * @param model
	 * @param args
	 * @return params
	 */
	public static List<Object> getEntityArgs(String sql, BaseEntity model, Object...args) throws Exception {
//		find ${*}
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^}]]+\\s*}").matcher(sql);
//		defined params
		List<Object> params = new ArrayList<>();
//		get class
		Class<?> objectClass = model.getClass();
//		get property descriptors
		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
//		find attr
		int i=0;
		while(matcher.find()){
//			get attr
			String attr = matcher.group().replace("${", "").replace("}", "").trim();
//			defined is find
			boolean isFind = false;
//			find from model
			for(PropertyDescriptor propertyDescriptor : propertyDescriptors){
//				found
				if(attr.equalsIgnoreCase(propertyDescriptor.getName())||attr.equalsIgnoreCase(StrUtil.toUnderlineCase(propertyDescriptor.getName()))){
//					get read method
					Method method = propertyDescriptor.getReadMethod();
//					get object value
					Object attrObject = method.invoke(model);
//					add param from model
					params.add(attrObject);
//					set is find true
					isFind = true;
//					break
					break;
				}
			}
//			if not find
			if(!isFind){
//				if model info contains
				if(model.getInfo().containsKey(attr)){
//					add param from model info
					params.add(model.getInfo().get(attr));
//					set is find true
					isFind = true;
				}
			}
//			if not find
			if(!isFind){
//				add param from args
				params.add(args[i]);
				i++;
			}
		}
//		return
		return params;
	}

	/**
	 * encode
	 * @param content
	 * @return content
	 */
	public static String encode(String content) {
		return content==null?"":content.replaceAll("<", "").replaceAll(">", "").trim();
	}

	/**
	 * escape
	 * @param content
	 * @return content
	 */
	public static String escape(String content) {
		return content==null?"":content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\[", "\\\\[").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
	}

}
