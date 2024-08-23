package com.dlshouwen.swda.core.jdbc.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;

import cn.hutool.core.util.StrUtil;

/**
 * sql utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class SqlUtils {

	/**
	 * get args key
	 * @param values
	 * @param splitChar
	 * @return attrs
	 */
	public static String getArgsKey(String values, String splitChar) {
		if (values == null || "".equals(values.trim())) {
			return "?";
		}
		StringBuilder attrs = new StringBuilder();
		for (int i = 0; i < values.split(splitChar).length; i++) {
			attrs.append("?").append(",");
		}
		if (attrs.length() == 0) {
			return "?";
		} else {
			attrs.deleteCharAt(attrs.length() - 1);
		}
		return attrs.toString();
	}

	/**
	 * get args value
	 * @param values
	 * @param splitChar
	 * @return values
	 */
	public static List<Object> getArgsValue(String values, String splitChar) {
		List<Object> list = new ArrayList<>();
		if (values == null || "".equals(values.trim())) {
			list.add("");
			return list;
		}
		for (String value : values.split(splitChar)) {
			list.add(value);
		}
		if (list.size() == 0) {
			list.add("");
		}
		return list;
	}

	/**
	 * get args integer value
	 * @param values
	 * @param splitChar
	 * @return values
	 */
	public static List<Object> getArgsIntegerValue(String values, String splitChar) {
		List<Object> list = new ArrayList<Object>();
		if (values == null || "".equals(values.trim())) {
			return list;
		}
		for (String value : values.split(splitChar)) {
			list.add(Integer.parseInt(value));
		}
		if (list.size() == 0) {
		}
		return list;
	}

	/**
	 * get object sql
	 * @param sql
	 * @return object sql
	 */
	public static String getObjectSql(String sql) {
		return sql.replaceAll("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}", "?");
	}

	/**
	 * get object args
	 * @param sql
	 * @param object
	 * @param args
	 * @return values
	 */
	public static List<Object> getObjectArgs(String sql, Object object, Object... args) {
//		match ${}
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}").matcher(sql);
//		defined params
		List<Object> params = new ArrayList<Object>();
//		get object class
		Class<?> objectClass = object.getClass();
//		get property descriptors
		PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[0];
		try {
			propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
//		defined mark
		int i = 0;
//		for each find
		while (matcher.find()) {
//			get attr
			String attr = matcher.group().replace("${", "").replace("}", "").trim();
//			to camel
			String _attr = StrUtil.toCamelCase(attr);
//			is find
			boolean isFind = false;
//			for each property
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
//				find
				if (attr.equalsIgnoreCase(propertyDescriptor.getName()) || _attr.equalsIgnoreCase(propertyDescriptor.getName())) {
//					get method
					Method method = propertyDescriptor.getReadMethod();
//					get attr object
					Object attrObject = null;
					try {
						attrObject = method.invoke(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
//					add params
					params.add(attrObject);
//					find
					isFind = true;
//					break
					break;
				}
			}
//			if not find
			if (!isFind) {
//				add args
				params.add(args[i]);
//				mark ++
				i++;
			}
		}
//		return params
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
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}").matcher(sql);
//		defined params
		List<Object> params = new ArrayList<Object>();
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
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}").matcher(sql);
//		defined params
		List<Object> params = new ArrayList<Object>();
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
		return content==null?"":content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\[", "\\\\[").replaceAll("\\%", "\\\\%").replaceAll("\\_", "\\\\_");
	}

}
