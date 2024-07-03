package com.dlshouwen.swda.core.utils;

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

import com.dlshouwen.swda.core.entity.BaseEntity;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * SQL工具类
 * </p>
 * 
 * @author 大连首闻科技有限公司
 * @since 0.0.1-SNAPSHOT
 */
public class SqlUtils {

	/**
	 * <p>
	 * 获取Preparestatement方式SQL的Key值
	 * </p>
	 * 用于获取Preparestatement方式SQL的key值，传入值后将会依据内容个数生成对应的?占位符
	 * 
	 * @param values    值列表，将依据该值列表决定生成对应的?占位符数量
	 * @param splitChar 分隔符，值列表的分割符号，通常为英文逗号,
	 * @return Preparestatement方式SQL的Key值
	 * @throws Exception 抛出全部异常
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
	 * <p>
	 * 获取Preparestatement方式SQL的值列表
	 * </p>
	 * 用于获取Preparestatement方式SQL的值列表，传入值后将会依据内容生成List<Object>列表
	 * 
	 * @param values    值列表，将依据该值列表决定生成List<Object>类型的值列表内容及顺序
	 * @param splitChar 分隔符，值列表的分割符号，通常为英文逗号,
	 * @return Preparestatement方式SQL的值列表
	 * @throws Exception 抛出全部异常
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
	 * <p>
	 * 获取Preparestatement方式SQL的值列表(Integer)
	 * </p>
	 * 用于获取Preparestatement方式SQL的值列表，传入值后将会依据内容生成List<Object>列表
	 * 
	 * @param values    值列表，将依据该值列表决定生成List<Object>类型的值列表内容及顺序
	 * @param splitChar 分隔符，值列表的分割符号，通常为英文逗号,
	 * @return Preparestatement方式SQL的值列表
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
	 * <p>
	 * 获取对象操作的SQL
	 * </p>
	 * 用于获取对象操作的SQL，对象的参数格式采用${参数名}的方式进行匹配，该方法可以替换改格式参数为?占位符
	 * 
	 * @param sql 原对象操作SQL
	 * @return 经过替换占位符处理的SQL
	 */
	public static String getObjectSql(String sql) {
		return sql.replaceAll("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}", "?");
	}

	/**
	 * <p>
	 * 获取对象操作参数列表
	 * </p>
	 * 用于获取对象操作的参数列表，对象的参数格式采用${参数名}的方式进行匹配，
	 * 该方法根据传入的SQL内容匹配SQL中所有符合条件的参数格式，获取参数名后通过对象反射一一查找object对象内匹配的属性，
	 * 若匹配上，则该位置将填入对象参数值，若不能匹配，则从此节点顺序开始查找对应的追加参数， 若查找到，则追加到参数列表中，若长度溢出，则抛出异常。
	 * 
	 * @param sql    原对象操作SQL
	 * @param object 通过反射查找对应属性的对象
	 * @param args   若对象未能匹配到属性则通过该对象数据顺次追加参数值
	 * @return 对象操作的参数列表
	 * @throws Exception 抛出全部异常
	 */
	public static List<Object> getObjectArgs(String sql, Object object, Object... args) {
		// Match匹配SQL的参数内容，使用${}方式Match
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}").matcher(sql);
		// 定义参数列表
		List<Object> params = new ArrayList<Object>();
		// 获取对象的类
		Class<?> objectClass = object.getClass();
		// 获取属性描述
		PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[0];
		try {
			propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		// 定义可变参数下标
		int i = 0;
		while (matcher.find()) {
			// 获取实际参数
			String attr = matcher.group().replace("${", "").replace("}", "").trim();
			String _attr = StrUtil.toCamelCase(attr);
			// 设置是否找到
			boolean isFind = false;
			// 内循环查找对应属性
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (attr.equalsIgnoreCase(propertyDescriptor.getName())
						|| _attr.equalsIgnoreCase(propertyDescriptor.getName())) {
					// 映射方法并通过反射获取参数
					Method method = propertyDescriptor.getReadMethod();
					Object attrObject = null;
					try {
						attrObject = method.invoke(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					// 参数赋值并跳出循环继续Match
					params.add(attrObject);
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				// 如果没找到对应参数，则添加args的参数
				params.add(args[i]);
				i++;
			}
		}
		return params;
	}

	/**
	 * <p>
	 * 获取Map操作参数列表
	 * </p>
	 * 用于获取Map对象操作的参数列表，对象的参数格式采用${参数名}的方式进行匹配，
	 * 该方法根据传入的SQL内容匹配SQL中所有符合条件的参数格式，获取参数名后通过对象反射一一查找object对象内匹配的属性，
	 * 若匹配上，则该位置将填入对象参数值，若不能匹配，则从此节点顺序开始查找对应的追加参数， 若查找到，则追加到参数列表中，若长度溢出，则抛出异常。
	 * 
	 * @param sql    原对象操作SQL
	 * @param object 通过反射查找对应属性的对象
	 * @param args   若对象未能匹配到属性则通过该对象数据顺次追加参数值
	 * @return 对象操作的参数列表
	 */
	public static List<Object> getMapArgs(String sql, Map<String, Object> object, Object... args) {
		// Match匹配SQL的参数内容，使用${}方式Match
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}").matcher(sql);
		/* 定义参数列表 */
		List<Object> params = new ArrayList<Object>();
		// 定义可变参数下标
		int i = 0;
		while (matcher.find()) {
			// 获取实际参数
			String attr = matcher.group().replace("${", "").replace("}", "").trim();
			if (object.containsKey(attr)) {
				// 如果没找到对应参数，则添加args的参数
				params.add(object.get(attr));
			} else {
				// 如果没找到对应参数，则添加args的参数
				params.add(args[i]);
				i++;
			}
		}
		return params;
	}

	/**
	 * <p>
	 * 获取扩展操作参数列表
	 * </p>
	 * 用于获取扩展操作的参数列表，读取对象的参数格式采用${参数名}的方式进行匹配，
	 * 该方法根据传入的SQL内容匹配SQL中所有符合条件的参数格式，获取参数名后通过对象反射一一查找object对象内匹配的属性，
	 * 若匹配上，则该位置将填入对象参数值，若不能匹配，则查找object对象内Info内匹配属性，若依然未找到，则从此节点顺序开始查找对应的追加参数，
	 * 若查找到，则追加到参数列表中，若长度溢出，则抛出异常。
	 * 
	 * @param sql   原对象操作SQL
	 * @param model 通过反射查找对应属性的对象
	 * @param args  若对象未能匹配到属性则通过该对象数据顺次追加参数值
	 * @return 对象操作的参数列表
	 * @throws Exception 抛出全部异常
	 */
	public static List<Object> getExtraArgs(String sql, BaseEntity model, Object... args) throws Exception {
		// Match匹配SQL的参数内容，使用${}方式Match
		Matcher matcher = Pattern.compile("\\$\\{\\s*[\\S&&[^\\}]]+\\s*\\}").matcher(sql);
		// 定义参数列表
		List<Object> params = new ArrayList<Object>();
		// 获取对象的类
		Class<?> objectClass = model.getClass();
		// 获取属性描述
		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
		// 定义可变参数下标
		int i = 0;
		while (matcher.find()) {
			// 获取实际参数
			String attr = matcher.group().replace("${", "").replace("}", "").trim();
			// 设置是否找到
			boolean isFind = false;
			// 内循环查找对应属性
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (attr.equalsIgnoreCase(propertyDescriptor.getName())) {
					// 映射方法并通过反射获取参数
					Method method = propertyDescriptor.getReadMethod();
					Object attrObject = method.invoke(model);
					// 参数赋值并跳出循环继续Match
					params.add(attrObject);
					isFind = true;
					break;
				}
			}
			// 如果没找到，则找对象Info中的
			if (!isFind) {
				if (model.getInfo().containsKey(attr)) {
					// 如果找到对应参数，则添加args的参数
					params.add(model.getInfo().get(attr));
					isFind = true;
				}
			}
			// 如果Info中没找到，则赋值args
			if (!isFind) {
				params.add(args[i]);
				i++;
			}
		}
		return params;
	}

	/**
	 * <p>
	 * 处理参数中的字符
	 * </p>
	 * 去掉所有特殊符号，并转null为空
	 * 
	 * @param content 原内容
	 * @return 处理后内容
	 */
	public static String encode(String content) {
		if (content == null) {
			return "";
		}
		return content.replaceAll("<", "").replaceAll(">", "").trim();
	}

	/**
	 * <p>
	 * 对内容进行转义，使用转义字符：\
	 * </p>
	 * 用于对特殊字符进行转义，目前MSSQL主要需要提供的转义字符：\ % [ _
	 * 
	 * @param content 原内容
	 * @return 转义后内容
	 */
	public static String escape(String content) {
		if (content == null) {
			return content;
		}
		return content.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\[", "\\\\[").replaceAll("\\%", "\\\\%")
				.replaceAll("\\_", "\\\\_");
	}

}
