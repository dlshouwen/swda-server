package com.dlshouwen.swda.core.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dlshouwen.swda.core.excel.utils.PoiUtils;
import com.dlshouwen.swda.core.jdbc.utils.DBUtils;
import com.dlshouwen.swda.core.jdbc.utils.PageUtils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

/**
 * init data utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class InitDataUtils {
	
	/** logger */
	private static Logger logger = LoggerFactory.getLogger(InitDataUtils.class);
	
	/**
	 * main
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void main(String[] _args) throws Exception {
//		const create
		boolean create = true;
//		const pager
		boolean pager = true;
//		const tables
		String[] tables = new String[] {};
//		const db info
		String driverClass = "com.mysql.cj.jdbc.Driver";
		String jdbcUrl = "jdbc:mysql://localhost:3306/swda?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
		String username = "root";
		String password = "root";
//		get path
		String path = System.getProperty("user.dir")+"/../swda-source/db";
//		get db type
		String dbType = "";
		if(driverClass.toLowerCase().contains("mysql")) {
			dbType = "mysql";
		}
//		defined s, e
		long s = System.currentTimeMillis();
		long e = System.currentTimeMillis();
//		defined: now
		Date now = new Date();
//		get data souce
		DataSource dataSource = DBUtils.getDataSource(driverClass, jdbcUrl, username, password);
//		get template
		JdbcTemplate template = new JdbcTemplate(dataSource);
//		get host, schema
		String host = jdbcUrl.substring(jdbcUrl.lastIndexOf("//")+1, jdbcUrl.lastIndexOf("/"));
		String schema = jdbcUrl.substring(jdbcUrl.lastIndexOf("=")+1);
//		logger
		e=System.currentTimeMillis();logger.info("init databse completed in "+(e-s)+" ms.");s=e;
//		need create
		if(create) {
//			get command
			String command = "";
//			command: mysql
			if(dbType.equals("mysql")) {
				command = "sqlcmd -s "+host+" -U "+username+" -P "+password+" -d "+schema+" -i \""+path+"/init/mysql.sql\"";
			}
//			execute
			Process process = Runtime.getRuntime().exec(command);
//			await
			while(process.isAlive()) {
				Thread.sleep(1000);
			}
//			log
			e=System.currentTimeMillis();logger.info("create tables views index functions complete in "+(e-s)+" ms.");s=e;
		}
//		defined table list
		List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
//		get workbook
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(path+"/init/datas.xlsx")));
//		get sheet
		XSSFSheet sheet = workbook.getSheetAt(0);
//		for each sheet row
		for (int rowno=1; rowno<=sheet.getLastRowNum(); rowno++) {
//			get row
			XSSFRow row = sheet.getRow(rowno);
//			set to table
			Map<String, Object> table = new HashMap<String, Object>();
			table.put("table_id", PoiUtils.getString(row.getCell(0)));
			table.put("table_code", PoiUtils.getString(row.getCell(1)));
			table.put("table_comment", PoiUtils.getString(row.getCell(2)));
			table.put("pre_execute_sql", PoiUtils.getString(row.getCell(3)));
			table.put("next_execute_sql", PoiUtils.getString(row.getCell(4)));
//			tables
			if(tables.length>0&&Arrays.stream(tables).filter(_table->_table.equals(MapUtil.getStr(table, "table_code"))).count()==0) {
				continue;
			}
//			get sheet
			XSSFSheet dsheet = workbook.getSheetAt(rowno);
//			if town
			if(table.get("table_code").equals("bms_town")) {
//				set dsheet
				dsheet = new XSSFWorkbook(new FileInputStream(new File(path+"/init/town.xlsx"))).getSheetAt(0);
			}
//			defined field sql, value sql
			StringBuffer fieldSql = new StringBuffer();
			StringBuffer valueSql = new StringBuffer();
//			for each header cells
			for (int cellno=0; cellno<=dsheet.getRow(0).getPhysicalNumberOfCells(); cellno++) {
//				get cell
				Cell cell = dsheet.getRow(0).getCell(cellno);
//				get field
				String field = cell==null?null:cell.getStringCellValue();
//				ignore
				if(field==null||field.isEmpty()||field.startsWith("ignore")) {
					continue;
				}
				fieldSql.append(field).append(", ");
				valueSql.append("?, ");
			}
//			delete last char
			fieldSql.delete(fieldSql.length()-2, fieldSql.length());
			valueSql.delete(valueSql.length()-2, valueSql.length());
//			put insert sql
			table.put("insert_sql", "insert into "+row.getCell(1)+" ("+fieldSql+") values ("+valueSql+")");
//			defined datas
			List<Object[]> args = new ArrayList<Object[]>();
//			for each rows
			for (int drowno=1; drowno<=dsheet.getLastRowNum(); drowno++) {
//				defined arg
				List<Object> arg = new ArrayList<Object>();
//				for each cell
				for (int dcellno=0; dcellno<=dsheet.getRow(drowno).getPhysicalNumberOfCells(); dcellno++) {
//					get cell
					Cell cell = dsheet.getRow(0).getCell(dcellno);
//					get field
					String field = cell==null?null:cell.getStringCellValue();
//					ignore
					if(field==null||field.isEmpty()||field.startsWith("ignore")) {
						continue;
					}
//					get data cell
					Cell dcell = dsheet.getRow(drowno).getCell(dcellno);
//					get value
					Object value = PoiUtils.getString(dcell).trim();
//					reset null
					value = value.equals("@null")?null:value;
//					reset data
					value = "@sysdate".equals(value)?now:value;
//					add to arg
					arg.add(value);
				}
//				add to args
				args.add(arg.toArray());
			}
			if(args.size()<=0) {
				continue;
			}
//			put args to table
			table.put("args", args);
//			add table to table list
			tableList.add(table);
		}
//		log
		e=System.currentTimeMillis();logger.info("construct datas from init excel complete in "+(e-s)+" ms.");s=e;
//		for each table list
		for(Map<String, Object> table : tableList) {
//			get pre execute sql
			String preExecuteSql = MapUtil.getStr(table, "pre_execute_sql");
//			is not empty
			if(StrUtil.isNotEmpty(preExecuteSql)) {
//				update
				template.update(preExecuteSql);
			}
//			log
			e=System.currentTimeMillis();logger.info("  table "+MapUtil.getStr(table, "table_code")+" pre execute complete in "+(e-s)+" ms.");s=e;
//			get args
			@SuppressWarnings("unchecked")
			List<Object[]> args = (List<Object[]>)table.get("args");
//			is pager
			if(pager) {
//				batch update
				PageUtils.batchUpdateArgs(template, MapUtil.getStr(table, "insert_sql"), args, 2000);
			}else {
//				defined current
				int current = 1;
//				for each args
				for(Object[] arg : args) {
//					update
					template.update(MapUtil.getStr(table, "insert_sql"), arg);
//					log
					e=System.currentTimeMillis();logger.info("    --> "+current+"/"+args.size()+" done in "+(e-s)+" ms.");s=e;
				}
			}
//			log
			e=System.currentTimeMillis();logger.info("  table "+MapUtil.getStr(table, "table_code")+" import completed, size: "+(args==null?0:args.size())+", cost "+(e-s)+" ms.");s=e;
//			get next execute sql
			String nextExecuteSql = MapUtil.getStr(table, "next_execute_sql");
//			is not empty
			if(StrUtil.isNotEmpty(nextExecuteSql)) {
//				update
				template.update(nextExecuteSql);
			}
//			log
			e=System.currentTimeMillis();logger.info("  table "+MapUtil.getStr(table, "table_code")+" next execute complete in "+(e-s)+" ms.");s=e;
		}
	}

}