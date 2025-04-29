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
public class SwFormItem {
	
	/** db */
	private static String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/swda?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
	private static String USERNAME = "root";
	private static String PASSWORD = "root";
	
	/** table */
	private static String TABLE = "bms_job";
	private static String MODEL = "job";
	
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
//		defined items, props, descripts
		StringBuffer items = new StringBuffer();
		StringBuffer props = new StringBuffer();
		StringBuffer descripts = new StringBuffer();
//		for each column
		for (Map<String, Object> columnInfo : columnList) {
//			get field type isnull key comment length
			String field = MapUtil.getStr(columnInfo, "Field").toLowerCase();
			String type = MapUtil.getStr(columnInfo, "Type").toLowerCase();
			boolean required = "no".equalsIgnoreCase(MapUtil.getStr(columnInfo, "Null").toLowerCase());
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
//			defined valid
			String valid = "";
//			get valid
			{
//				required
				valid += required?"r":"";
//				code
				if(field.contains("code")) valid += "|english_number";
//				phone
				else if (field.contains("phone")) valid += "|phone";
//				email
				else if (field.contains("mail")) valid += "|email";
//				mobile
				else if (field.contains("mobile")) valid += "|mobile";
//				url
				else if (field.contains("url")) valid += "|url";
//				qq
				else if (field.contains("qq")) valid += "|qq";
//				card
				else if (field.contains("card")&&field.contains("id")) valid += "|card18";
//				other
				else {
//					integer
					valid += "Integer".equals(dataType)?(valid.length()>0?("|"+"integer"):("integer")):"";
//					double
					valid += ("Long".equals(dataType)||"Double".equals(dataType))?(valid.length()>0?("|"+"double"):("double")):"";
//					date
					valid += "Date".equals(dataType)?(valid.length()>0?("|"+"date"):("date")):"";
				}
//				length
				valid += length>0?(valid.length()>0?("|"+"l-le"+length):("l-le"+length)):"";
			}
//			get valid props
			String pvalid = valid.length()>0?("valid=\""+valid+"\" "):"";
//			items
			{
//				type: remark
				if(field.contains("remark")) {
//					append items
					items.append("<sw-input type=\"textarea\" prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" :autosize=\"{ minRows:2, maxRows:4 }\" ").append(pvalid).append("/>\n");
				} else
//				type: system
				if(field.contains("system")&&field.contains("id")) {
//					append items
					items.append("<sw-select-system prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
				} else
//				type: menu
				if(field.contains("menu")&&field.contains("id")) {
//					append items
					items.append("<sw-select-menu prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
				} else
//				type: role
				if(field.contains("role")&&field.contains("id")) {
//					append items
					items.append("<sw-select-role prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
				} else
//				type: organ
				if(field.contains("organ")&&field.contains("id")) {
//					append items
					items.append("<sw-select-organ prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
				} else
//				type: post
				if(field.contains("post")&&field.contains("id")) {
//					append items
					items.append("<sw-select-post prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
				} else
//				type: region
				if((field.contains("province")||field.contains("region"))&&field.contains("id")) {
//					append items
					items.append("<sw-select-region prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
				} else {
//					type: dict
					if(dict) {
//						append items
						items.append("<sw-radio-group prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" dict=\"").append(field).append("\" ").append(pvalid).append("/>\n");
					} else {
//						type: string
						if("String".equals(dataType)) {
							items.append("<sw-input prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
						}
//						type: double
						if("Integer".equals(dataType)||"Long".equals(dataType)||"Double".equals(dataType)) {
							items.append("<sw-input type=\"number\" prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
						}
//						type: date
						if("Date".equals(dataType)) {
							items.append("<sw-date-picker prop=\"").append(cfield).append("\" v-model=\"").append(MODEL).append(".").append(cfield).append("\" label=\"").append(comment).append("\" ").append(pvalid).append("/>\n");
						}
					}
				}
			}
//			props
			props.append(cfield).append(": ").append("Integer".equals(dataType)||"Long".equals(dataType)||"Double".equals(dataType)?"0":"''").append(",\n");
//			descripts
			{
//				start
				descripts.append("<el-descriptions-item label=\"").append(comment).append("\">");
//				type: dict
				if("String".equals(dataType)&&length>0&&length<=2) {
					descripts.append("\n	<el-tag :type=\"appStore.dict.").append(field).append("[").append(MODEL).append(".").append(cfield).append("].style\">{{appStore.dict.").append(field).append("[").append(MODEL).append(".").append(cfield).append("].label}}</el-tag>\n");
				}else {
					descripts.append("{{").append(MODEL).append(".").append(cfield).append("}}");
				}
//				end
				descripts.append("</el-descriptions-item>\n");
			}
		}
//		output
		System.out.println("------------------------------------------------------------------------------");
		System.out.print(items);
		System.out.println("------------------------------------------------------------------------------");
		System.out.print(props);
		System.out.println("------------------------------------------------------------------------------");
		System.out.print(descripts);
		System.out.println("------------------------------------------------------------------------------");
	}

}