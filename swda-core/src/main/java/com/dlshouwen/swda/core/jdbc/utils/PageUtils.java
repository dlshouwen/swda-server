package com.dlshouwen.swda.core.jdbc.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * page utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class PageUtils {

	/**
	 * batch update args
	 * @param template
	 * @param sql
	 * @param args
	 * @param buffer
	 */
	public static int[] batchUpdateArgs(JdbcTemplate template, String sql, List<Object[]> args, int buffer) {
		if(args==null||args.size()<=0) {
			return null;
		}
		List<Integer> returns = new ArrayList<>();
		int pageCount = (args.size()-1)/buffer+1;
		for(int i=0; i<pageCount; i++) {
			int start = i*buffer;
			int end = start + buffer;
			if(end>=args.size()) {
				end = args.size();
			}
			List<Object[]> batchArgs = args.subList(start, end);
			int[] _returns = template.batchUpdate(sql, batchArgs);
			for(int _return : _returns)
				returns.add(_return);
		}
		return returns.stream().mapToInt(Integer::valueOf).toArray();
	}

	/**
	 * batch update
	 * @param template
	 * @param sql
	 * @param dataList
	 * @param buffer
	 */
	public static <T> int[] batchUpdate(JdbcTemplate template, String sql, List<T> dataList, int buffer) {
		if(dataList==null||dataList.size()<=0) {
			return null;
		}
		List<Object[]> args = new ArrayList<>();
		for(T data : dataList) {
			args.add(SqlUtils.getObjectArgs(sql, data).toArray());
		}
		return batchUpdateArgs(template, SqlUtils.getObjectSql(sql), args, buffer);
	}

	/**
	 * batch update
	 * @param template
	 * @param sqlList
	 * @param buffer
	 */
	public static int[] batchUpdate(JdbcTemplate template, List<String> sqlList, int buffer) {
		if(sqlList==null||sqlList.size()<=0) {
			return null;
		}
		List<Integer> returns = new ArrayList<>();
		int pageCount = (sqlList.size()-1)/buffer+1;
		for(int i=0; i<pageCount; i++) {
			int start = i*buffer;
			int end = start + buffer;
			if(end>=sqlList.size()) {
				end = sqlList.size();
			}
			List<String> batchSqlList = sqlList.subList(start, end);
			int[] _returns = template.batchUpdate(batchSqlList.toArray(new String[0]));
			for(int _return : _returns)
				returns.add(_return);
		}
		return returns.stream().mapToInt(Integer::valueOf).toArray();
	}
	
}
