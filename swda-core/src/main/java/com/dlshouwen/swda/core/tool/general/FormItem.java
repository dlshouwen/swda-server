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
public class FormItem {
	
	/** db */
	private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/swda?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	
	/** table */
	private static String TABLE = "bms_role";
	private static String MODEL = "role";
	
	
	
	/**
	 * main
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] _args) throws Exception {
//		ignore fields
		String[] ignoreFields = new String[]{"tenant_id", "version", "creator", "create_time", "updater", "update_time", "deleted", "deleter", "delete_time"};
//		get data souce
		DataSource dataSource = DBUtils.getDataSource(DRIVER_CLASS, JDBC_URL, USERNAME, PASSWORD);
//		get template
		JdbcTemplate template = new JdbcTemplate(dataSource);
//		get column list
		List<Map<String, Object>> columnList = template.queryForList("show full columns from " + TABLE.toLowerCase());
//		defined items, props, rules
		StringBuffer items = new StringBuffer();
		StringBuffer props = new StringBuffer();
		StringBuffer rules = new StringBuffer();
//		for each column
		for (Map<String, Object> columnInfo : columnList) {
//			get field type isnull key comment length
			String field = MapUtil.getStr(columnInfo, "Field").toLowerCase();
			String type = MapUtil.getStr(columnInfo, "Type").toLowerCase();
			String required = "no".equalsIgnoreCase(MapUtil.getStr(columnInfo, "Null").toLowerCase())?"r":"";
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
//			get length
			int length = 0;
			try {
				length = Integer.parseInt(type.replaceAll("\\D", ""));
			}catch(Exception e) {
				
			}
//			get valid
			String valid = required;
			valid += "Integer".equals(dataType)?(valid.length()>0?("|"+"integer"):("integer")):"";
			valid += ("Long".equals(dataType)||"Double".equals(dataType))?(valid.length()>0?("|"+"double"):("double")):"";
			valid += "Date".equals(dataType)?(valid.length()>0?("|"+"date"):("date")):"";
			valid += length>0?(valid.length()>0?("|"+"l-le"+length):("l-le"+length)):"";
//			start
			items.append("<el-form-item prop=\"").append(StrUtil.toCamelCase(field)).append("\" label=\"").append(comment).append("\">\n");
//			type: dict
			if("String".equals(dataType)&&length>0&&length<=2) {
				items.append("	<el-radio-group v-model=\"").append(MODEL).append(".").append(StrUtil.toCamelCase(field)).append("\">\n");
				items.append("		<el-radio-button v-for=\"item in appStore.dict.").append(field).append(".datas\" :label=\"item.label\" :value=\"item.value\" />\n");
				items.append("	</el-radio-group>\n");
			}
//			type: string
			if("String".equals(dataType)&&(length<=0||length>2)) {
				items.append("	<el-input v-model=\"").append(MODEL).append(".").append(StrUtil.toCamelCase(field)).append("\" placeholder=\"请输入").append(comment).append("\"></el-input>\n");
			}
//			type: double
			if("Integer".equals(dataType)||"Long".equals(dataType)||"Double".equals(dataType)) {
				items.append("	<el-input-number v-model=\"").append(MODEL).append(".").append(StrUtil.toCamelCase(field)).append("\" :min=\"0\" placeholder=\"请输入").append(comment).append("\"></el-input-number>\n");
			}
//			type: date
			if("Date".equals(dataType)) {
				items.append("	<el-date-picker v-model=\"").append(MODEL).append(".").append(StrUtil.toCamelCase(field)).append("\" type=\"datetime\" format=\"YYYY-MM-DD HH:mm:ss\" placeholder=\"请选择").append(comment).append("\" />\n");
			}
//			end
			items.append("</el-form-item>\n");
//			props
			props.append(StrUtil.toCamelCase(field)).append(": '").append("Integer".equals(dataType)||"Long".equals(dataType)||"Double".equals(dataType)?"0":"").append("',\n");
//			rules
			rules.append(StrUtil.toCamelCase(field)).append(": [{ label:'").append(comment).append("', valid:'").append(valid).append("', lang:t, validator:validator, trigger:'blur' }],\n");
		}
//		output
		System.out.println(items);
		System.out.println(props);
		System.out.println(rules);
	}

}