package com.dlshouwen.swda.extra.spring.mapper;

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
import java.util.Date;

/**
 * class row mapper
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class ClassRowMapper<T> implements RowMapper<T> {

	/** mapped class */
	private Class<T> _class;

	/**
	 * construct
	 * @param _class
	 */
	public ClassRowMapper(Class<T> _class){
		this._class = _class;
	}

	/**
	 * map row
	 * @param resultSet
	 * @param rowNo
	 * @throws SQLException
	 */
	@Override
	public T mapRow(ResultSet resultSet, int rowNo) throws SQLException {
//		instance object
		T instance = null;
		try {
//			new instance
			instance = _class.getDeclaredConstructor().newInstance();
//			get property descriptors
			PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(_class);
//			get meta data
			ResultSetMetaData md = resultSet.getMetaData();
//			get column count
			int columnCount = md.getColumnCount();
//			for each column
			for (int i=1; i<=columnCount; i++) {
//				defined is set to info
				boolean isSetToInfo = true;
//				get column name  / value
				String columnName = StrUtil.toCamelCase(md.getColumnLabel(i));
				Object value = resultSet.getObject(i);
//				find property
				for (PropertyDescriptor pd : pds) {
//					found
					if(pd.getName().equalsIgnoreCase(columnName)&&pd.getWriteMethod()!=null) {
//						set set to info false
						isSetToInfo = false;
//						invoke value
						invokeValue(instance, pd, value);
						break;
					}
				}
//				need set to info
				if(isSetToInfo) {
//					clear value
					value = value==null?"":value;
//					set info
					_class.getSuperclass().getMethod("setInfo", String.class, Object.class).invoke(instance, columnName, value);
				}
			}
		} catch (Exception e) {
//			print stack trace
			e.printStackTrace();
		}
//		return
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
//		if any null return
		if(instance==null||pd==null||value==null) {
			return;
		}
//		get write method
		Method writeMethod = pd.getWriteMethod();
		/**
		 * set value
		 */
//		value: Double
		if(value instanceof Double){
//			method: String
			if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.String")){
				writeMethod.invoke(instance, String.valueOf(value));
			}
//			method: other
			else {
				writeMethod.invoke(instance, value);
			}
		}
//		value: Integer
		else if(value instanceof Integer){
//			method: String
			if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.String")){
				writeMethod.invoke(instance, String.valueOf(value));
			}
//			method: other
			else {
				writeMethod.invoke(instance, value);
			}
		}
//		value: big decimal
		else if(value instanceof BigDecimal){
//			method: int
			if(pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("int")){
				writeMethod.invoke(instance, ((BigDecimal) value).intValue());
			}
//			method: long
			else if(pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("long")){
					writeMethod.invoke(instance, ((BigDecimal) value).longValue());
			}
//			method: double
			else if(pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("double")){
					writeMethod.invoke(instance, ((BigDecimal) value).doubleValue());
			}
//			method: float
			else if(pd.getReadMethod().getGenericReturnType().toString().equalsIgnoreCase("float")){
				writeMethod.invoke(instance, ((BigDecimal) value).floatValue());
			}
//			method: Long
			else if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.Long")){
				writeMethod.invoke(instance, ((BigDecimal) value).longValue());
			}
//			method: Double
			else if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.Double")){
				writeMethod.invoke(instance, ((BigDecimal) value).doubleValue());
			}
//			method: Float
			else if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.Float")){
				writeMethod.invoke(instance, ((BigDecimal) value).floatValue());
			}
//			method: other
			else{
				writeMethod.invoke(instance, ((BigDecimal) value));
			}
		}
//		value: timestame
		else if(value instanceof Timestamp){
//			method: Date
			if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.util.Date")){
				writeMethod.invoke(instance, new Date(((Timestamp)value).getTime()));
			}
//			method: LocalDateTime
			else if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.time.LocalDateTime")) {
				writeMethod.invoke(instance, ((Timestamp)value).toLocalDateTime());
			}
//			method: other
			else {
				writeMethod.invoke(instance, value);
			}
		}
//		value: other
		else{
//			method: String
			if(pd.getReadMethod().getGenericReturnType().toString().endsWith("java.lang.String")){
				writeMethod.invoke(instance, String.valueOf(value));
			}
//			method: other
			else {
				writeMethod.invoke(instance, value);
			}
		}
	}

}
