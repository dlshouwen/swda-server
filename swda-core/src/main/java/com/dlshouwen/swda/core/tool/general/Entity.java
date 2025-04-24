package com.dlshouwen.swda.core.tool.general;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dlshouwen.swda.core.jdbc.utils.DBUtils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

/**
 * init data utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class Entity {
	
	/** db */
	private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/swda?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	
	/** table */
	private static String BASE = "com.dlshouwen.swda";
	private static String SYSTEM = "bms";
	private static String MODEL = "system";
	private static String CLASS = "Town";
	private static String TABLE = "bms_town";
	private static String AUTHOR = "liujingcheng@live.cn";
	private static String VERSION = "1.0.0";
	
	
	/**
	 * main
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] _args) throws Exception {
//		get data souce
		DataSource dataSource = DBUtils.getDataSource(DRIVER_CLASS, JDBC_URL, USERNAME, PASSWORD);
//		get template
		JdbcTemplate template = new JdbcTemplate(dataSource);
//		get column list
		List<Map<String, Object>> columnList = template.queryForList("show full columns from " + TABLE.toLowerCase());
//		defined properties
		StringBuffer properties = new StringBuffer();
//		defined boolean
		boolean isDate = false;
		boolean isPrimary = false;
		boolean isIdType = false;
		boolean isVersionField = false;
		boolean isTableField = false;
//		for each column
		for (Map<String, Object> columnInfo : columnList) {
//			get field type isnull key comment length
			String field = MapUtil.getStr(columnInfo, "Field").toLowerCase();
			String type = MapUtil.getStr(columnInfo, "Type").toLowerCase();
			String key = MapUtil.getStr(columnInfo, "Key");
			String extra = MapUtil.getStr(columnInfo, "Extra").toLowerCase();
//			get data type
			String dataType = "String";
			{
//				date / timestamp
				if (type.toLowerCase().startsWith("date") || type.toLowerCase().startsWith("timestamp")) {
					// set date
					isDate = true;
					// set data type
					dataType = "LocalDateTime";
				}
//				tinyint / smallint / mediumint / int
				if (type.toLowerCase().startsWith("tinyint") || type.toLowerCase().startsWith("smallint") || type.toLowerCase().startsWith("mediumint") || type.toLowerCase().startsWith("int"))
					dataType = "Integer";
//				bigint
				if (type.toLowerCase().startsWith("bigint")) 
					dataType = "Long";
//				double / decimal
				if (type.toLowerCase().startsWith("double") || type.toLowerCase().startsWith("decimal"))
					dataType = "Double";
			}
//			primary
			if ("PRI".equalsIgnoreCase(key)) {
//				primary
				isPrimary = true;
//				auto increment
				if(extra.contains("auto_increment")) {
//					append properties
					properties.append("	@TableId").append("\n");
				}else {
//					set id type
					isIdType = true;
//					append properties
					properties.append("	@TableId(type=IdType.INPUT)").append("\n");
				}
			}
//			version
			if("version".equals(field)){
//				append properties
				properties.append("	@Version").append("\n");
//				set version field
				isVersionField = true;
			}
//			table field fill
			if("version".equals(field)||"creator".equals(field)||"create_time".equals(field)||"deleted".equals(field)){
//				append properties
				properties.append("	@TableField(fill=FieldFill.INSERT)").append("\n");
//				set table filed
				isTableField = true;
			}
			if("updater".equals(field)||"update_time".equals(field)){
//				append properties
				properties.append("	@TableField(fill=FieldFill.INSERT_UPDATE)").append("\n");
//				set table filed
				isTableField = true;
			}
//			append properties
			properties.append("	private ").append(dataType).append(" ").append(StrUtil.toCamelCase(field)).append(";").append("\n");
			properties.append("\n");
		}
//		append entity
		StringBuffer entity = new StringBuffer();
		entity.append("package ").append(BASE).append(".").append(SYSTEM).append(".").append(MODEL).append(".entity;").append("\n");
		entity.append("\n");
		entity.append("import com.dlshouwen.swda.core.mybatis.entity.BaseEntity;").append("\n");
		entity.append("\n");
		if(isTableField){
			entity.append("import com.baomidou.mybatisplus.annotation.FieldFill;").append("\n");
		}
		if (isIdType) {
			entity.append("import com.baomidou.mybatisplus.annotation.IdType;").append("\n");
		}
		if (isPrimary) {
			entity.append("import com.baomidou.mybatisplus.annotation.TableId;").append("\n");
		}
		if(isTableField){
			entity.append("import com.baomidou.mybatisplus.annotation.TableField;").append("\n");
		}
		entity.append("import com.baomidou.mybatisplus.annotation.TableName;").append("\n");
		if(isVersionField){
			entity.append("import com.baomidou.mybatisplus.annotation.Version;").append("\n");
		}
		entity.append("\n");
		if (isDate) {
			entity.append("import java.time.LocalDateTime;").append("\n");
			entity.append("\n");
		}
		entity.append("import lombok.Data;").append("\n");
		entity.append("import lombok.EqualsAndHashCode;").append("\n");
		entity.append("\n");
		entity.append("/**").append("\n");
		entity.append(" * ").append(TABLE.replace("_", "").replace(SYSTEM, "").trim()).append("").append("\n");
		entity.append(" * @author ").append(AUTHOR).append("\n");
		entity.append(" * @version ").append(VERSION).append("\n");
		entity.append(" */").append("\n");
		entity.append("@Data").append("\n");
		entity.append("@EqualsAndHashCode(callSuper=true)").append("\n");
		entity.append("@TableName(\"").append(TABLE).append("\")").append("\n");
		entity.append("public class ").append(CLASS).append(" extends BaseEntity {").append("\n");
		entity.append("\n");
		entity.append(properties);
		entity.append("}");
//		output
		System.out.println(entity);
	}

}