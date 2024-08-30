package com.dlshouwen.swda.core.jdbc.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

import cn.hutool.core.util.StrUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * class row mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ClassRowMapper<T> implements RowMapper<T> {

	/** mapped class */
	private Class<T> mappedClass;

	/**
	 * constructor
	 * @param mappedClass
	 */
	public ClassRowMapper(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
	}

	/**
	 * map row
	 * @param rs
	 * @param rowNum
	 * @throws SQLException
	 */
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T instance = null;
		try {
//			new instance
			instance = mappedClass.getDeclaredConstructor().newInstance();
//			get property descriptors
			PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
//			get result set meta data
			ResultSetMetaData md = rs.getMetaData();
//			get column count
			int columnCount = md.getColumnCount();
//			for each column
			for (int i = 1; i <= columnCount; i++) {
//				is set to info
				boolean isSetToInfo = true;
//				get column name, value
				String columnName = StrUtil.toCamelCase(md.getColumnName(i));
				Object value = rs.getObject(i);
//				for each filed
				for (PropertyDescriptor pd : pds) {
//					is column name
					if (pd.getName().equalsIgnoreCase(columnName)&&pd.getWriteMethod() != null) {
//						set to info
						isSetToInfo = false;
//						invoke value
						invokeValue(instance, pd, value);
//						break
						break;
					}
				}
//				set to info
				if (isSetToInfo) {
//					handle value
					value = value == null ? "" : value;
//					set info
					mappedClass.getSuperclass().getMethod("setInfo", String.class, Object.class).invoke(instance, columnName, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	/**
	 * invoke value
	 * @param instance
	 * @param pd
	 * @param value
	 * @throws Exception
	 */
	private void invokeValue(T instance, PropertyDescriptor pd, Object value) throws Exception {
		if (instance == null || pd == null || value == null) {
			return;
		}
		Method writeMethod = pd.getWriteMethod();
		if (value instanceof Double) {
			writeMethod.invoke(instance, value);
		} else if (value instanceof Integer) {
			writeMethod.invoke(instance, value);
		} else if (value instanceof BigDecimal) {
			if (pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("int")) {
				writeMethod.invoke(instance, ((BigDecimal) value).intValue());
			} else if (pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("long")) {
				writeMethod.invoke(instance, ((BigDecimal) value).longValue());
			} else if (pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("double")) {
				writeMethod.invoke(instance, ((BigDecimal) value).doubleValue());
			} else if (pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("float")) {
				writeMethod.invoke(instance, ((BigDecimal) value).floatValue());
			} else if (pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.Long")) {
				writeMethod.invoke(instance, ((BigDecimal) value).longValue());
			} else if (pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.Double")) {
				writeMethod.invoke(instance, ((BigDecimal) value).doubleValue());
			} else if (pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.Float")) {
				writeMethod.invoke(instance, ((BigDecimal) value).floatValue());
			} else {
				writeMethod.invoke(instance, ((BigDecimal) value));
			}
		} else if (value instanceof Timestamp) {
			writeMethod.invoke(instance, ((Timestamp) value).toLocalDateTime());
		} else {
			writeMethod.invoke(instance, value);
		}
	}

}
