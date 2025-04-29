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
public class SwTableColumn {
	
	/** db */
	private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/swda?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	
	/** table */
	private static String TABLE = "bms_job";
	
	
	/**
	 * main
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] _args) throws Exception {
//		ignore fields
		String[] ignoreFields = new String[]{"tenant_id", "version", "creator", "updater", "update_time", "deleted", "deleter", "delete_time"};
//		get data souce
		DataSource dataSource = DBUtils.getDataSource(DRIVER_CLASS, JDBC_URL, USERNAME, PASSWORD);
//		get template
		JdbcTemplate template = new JdbcTemplate(dataSource);
//		get column list
		List<Map<String, Object>> columnList = template.queryForList("show full columns from " + TABLE.toLowerCase());
//		defined columns
		StringBuffer columns = new StringBuffer();
//		for each column
		for (Map<String, Object> columnInfo : columnList) {
//			get field type isnull key comment length
			String field = MapUtil.getStr(columnInfo, "Field").toLowerCase();
			String type = MapUtil.getStr(columnInfo, "Type").toLowerCase();
			String comment = MapUtil.getStr(columnInfo, "Comment").toLowerCase();
//			get camel case field
			String cfield = StrUtil.toCamelCase(field);
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
//			get length
			int length = 0;
			try {
				length = Integer.parseInt(type.replaceAll("\\D", ""));
			}catch(Exception e) {
				
			}
//			get dict
			boolean dict = "String".equals(dataType) && length>0 && length<=2;
//			start
			columns.append("<sw-table-column ");
//			data type
			{
				if("String".equals(dataType)) columns.append("data-type=\"string\" ");
				if("Integer".equals(dataType)||"Long".equals(dataType)||"Double".equals(dataType)) columns.append("data-type=\"number\" ");
				if("LocalDateTime".equals(dataType)) columns.append("data-type=\"date\" ");
			}
//			prop
			columns.append("prop=\"").append(cfield).append("\" ");
//			dict
			if(dict) columns.append("dict=\"").append(field).append("\" ");
//			label
			columns.append("label=\"").append(comment).append("\" ");
//			width
			columns.append("width=\"").append("LocalDateTime".equals(dataType)?"180":"120").append("\" ");
//			fast
			{
//				dict
				if(dict) {
//					eq
					columns.append("fast=\"eq\" ");
				} else {
//					string: lk
					if("String".equals(dataType)) columns.append("fast=\"lk\" ");
//					integer / long: eq
					if("Integer".equals(dataType)||"Long".equals(dataType)) columns.append("fast=\"eq\" ");
//					double / local date time: range
					if("Double".equals(dataType)||"LocalDateTime".equals(dataType)) columns.append("fast=\"range\" ");
				}
			}
//			end
			columns.append("/>\n");
		}
//		output
		System.out.println("------------------------------------------------------------------------------");
		System.out.print(columns);
		System.out.println("------------------------------------------------------------------------------");
	}

}