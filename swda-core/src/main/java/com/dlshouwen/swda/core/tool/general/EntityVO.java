package com.dlshouwen.swda.core.tool.general;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dlshouwen.swda.core.jdbc.utils.DBUtils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * init data utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class EntityVO {
	
	/** db */
	private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/swda?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	
	/** table */
	private static String BASE = "com.dlshouwen.swda";
	private static String SYSTEM = "bms";
	private static String MODEL = "system";
	private static String CLASS = "TownVO";
	private static String TABLE = "bms_town";
	private static String AUTHOR = "liujingcheng@live.cn";
	private static String VERSION = "1.0.0";
	
	
	/**
	 * main
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] _args) throws Exception {
//		ignore fields
		String[] ignoreFields = new String[]{"tenant_id", "version", "creator", "updater", "update_time", "deleted", "deleter", "delete_time"};
//		defined not null fields
		String[] notNullFields = new String[]{"tenant_id", "version", "creator", "create_time", "updater", "update_time", "deleted", "deleter", "delete_time"};
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
		boolean isDateTimeFormat = false;
		boolean isNotBlank = false;
		boolean isLength = false;
		boolean isNotNull = false;
//		for each column
		for (Map<String, Object> columnInfo : columnList) {
//			get field type isnull key comment length
			String field = MapUtil.getStr(columnInfo, "Field").toLowerCase();
			String type = MapUtil.getStr(columnInfo, "Type").toLowerCase();
			String isNull = MapUtil.getStr(columnInfo, "Null").toLowerCase();
			String key = MapUtil.getStr(columnInfo, "Key");
			String comment = MapUtil.getStr(columnInfo, "Comment").toLowerCase();
			String length = type.replaceAll("\\D", "");
//			ignore
			if(ArrayUtil.contains(ignoreFields, field)) {
//				continue
				continue;
			}
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
//			append properties
			properties.append("	@Schema(description=\"").append(field.replace("_", " ")).append("\")").append("\n");
//			not primary
			if (!"PRI".equalsIgnoreCase(key)) {
//				varchar char text tinytext mediumtext longtext
				if (type.toLowerCase().startsWith("varchar") || type.toLowerCase().startsWith("char")
						|| type.toLowerCase().startsWith("text") || type.toLowerCase().startsWith("tinytext")
						|| type.toLowerCase().startsWith("mediumtext") || type.toLowerCase().startsWith("longtext")) {
//					not blank
					if ("no".equalsIgnoreCase(isNull) && !ArrayUtil.contains(notNullFields, field)) {
//						set not blank
						isNotBlank = true;
//						set properties
						properties.append("	@NotBlank(message=\"").append(comment).append("不能为空\")").append("\n");
					}
//					length
					if (!"".equals(length.trim())) {
//						set length
						isLength = true;
//						set properties
						properties.append("	@Length(min=0, max=").append(length).append(", message=\"").append(comment).append("长度必须在0-").append(length).append("之间\")").append("\n");
					}
				}
//				int
				if (type.toLowerCase().startsWith("tinyint") || type.toLowerCase().startsWith("smallint")
						|| type.toLowerCase().startsWith("mediumint") || type.toLowerCase().startsWith("int")
						|| type.toLowerCase().startsWith("bigint")) {
//					not null
					if ("no".equalsIgnoreCase(isNull.toLowerCase()) && !ArrayUtil.contains(notNullFields, field)) {
//						set not blank
						isNotNull = true;
//						set properties
						properties.append("	@NotNull(message=\"").append(comment).append("不能为空\")").append("\n");
					}
				}
//				date datetime
				if (type.toLowerCase().startsWith("date") || type.toLowerCase().startsWith("datetime")) {
//					set date, date time format
					isDate = true;
					isDateTimeFormat = true;
//					not null
					if ("no".toLowerCase().equalsIgnoreCase(isNull) && !ArrayUtil.contains(notNullFields, field)) {
//						set not blank
						isNotNull = true;
//						set properties
						properties.append("	@NotNull(message=\"").append(comment).append("不能为空\")").append("\n");
					}
//					format
					if ("date".toLowerCase().equalsIgnoreCase(type)) {
						properties.append("	@DateTimeFormat(pattern=\"yyyy-MM-dd\")").append("\n");
					} else {
						properties.append("	@DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")").append("\n");
					}
				}
			}
//			append properties
			properties.append("	private ").append(dataType).append(" ").append(StrUtil.toCamelCase(field)).append(";").append("\n");
			properties.append("\n");
		}
//		append entity
		StringBuffer entity = new StringBuffer();
		entity.append("package ").append(BASE).append(".").append(SYSTEM).append(".").append(MODEL).append(".vo;").append("\n");
		entity.append("\n");
		entity.append("import java.io.Serializable;").append("\n");
		if (isDate) {
			entity.append("import java.time.LocalDateTime;").append("\n");
		}
		entity.append("\n");
		entity.append("import io.swagger.v3.oas.annotations.media.Schema;").append("\n");
		entity.append("\n");
		entity.append("import lombok.Data;").append("\n");
		entity.append("\n");
		if (isNotNull) {
			entity.append("import jakarta.validation.constraints.NotNull;").append("\n");
		}
		if (isNotBlank) {
			entity.append("import jakarta.validation.constraints.NotBlank;").append("\n");
		}
		if (isDate || isNotNull || isNotBlank) {
			entity.append("\n");
		}
		if (isLength) {
			entity.append("import org.hibernate.validator.constraints.Length;").append("\n");
		}
		if (isDateTimeFormat) {
			entity.append("import org.springframework.format.annotation.DateTimeFormat;").append("\n");
		}
		entity.append("\n");
		entity.append("/**").append("\n");
		entity.append(" * ").append(TABLE.replace("_", "").replace(SYSTEM, "").trim()).append(" vo").append("\n");
		entity.append(" * @author ").append(AUTHOR).append("\n");
		entity.append(" * @version ").append(VERSION).append("\n");
		entity.append(" */").append("\n");
		entity.append("@Data").append("\n");
		entity.append("@Schema(description=\"").append(TABLE.replace("_", "").replace(SYSTEM, "").trim()).append("\")").append("\n");
		entity.append("public class ").append(CLASS).append(" implements Serializable {").append("\n");
		entity.append("\n");
		entity.append("	/** serial version uid */").append("\n");
		entity.append("	private static final long serialVersionUID = 1L;").append("\n");
		entity.append("\n");
		entity.append(properties);
		entity.append("}");
//		output
		System.out.println(entity);
	}

}