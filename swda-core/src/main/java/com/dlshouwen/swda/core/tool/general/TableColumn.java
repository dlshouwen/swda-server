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
public class TableColumn {
	
	/** db */
	private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/swda?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	
	/** table */
	private static String TABLE = "bms_region";
	
	
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
//			start
			columns.append("<el-table-column ");
//			label
			columns.append("label=\"").append(comment).append("\" ");
//			prop
			columns.append("prop=\"").append(StrUtil.toCamelCase(field)).append("\" ");
//			width
			{
//				set width
				String width = "LocalDateTime".equals(dataType)?"120":"90";
				columns.append("width=\"").append(width).append("\" ");
			}
//			default
			columns.append("align=\"center\" ");
			columns.append("sortable ");
			columns.append("show-overflow-tooltip ");
//			end
			columns.append("/>\n");
		}
//		output
		System.out.println(columns);
	}

}