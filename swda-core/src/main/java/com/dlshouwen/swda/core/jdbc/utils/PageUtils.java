package com.dlshouwen.swda.core.jdbc.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 分页工具类
 * @author liujingcheng@live.cn
 * @since hygea 6.0
 */
public class PageUtils {

	/**
	 * 分页执行SQL
	 * @param jt 数据连接对象
	 * @param sql Sql语句
	 * @param args 参数列表
	 * @param buffer 缓冲大小
	 */
	public static void batchUpdateArr(JdbcTemplate jt, String sql, List<Object[]> args, int buffer) throws Exception {
		if(args==null||args.size()<=0) {
			return;
		}
		int pageCount = (args.size()-1)/buffer+1;
		for(int i=0; i<pageCount; i++) {
			int start = i*buffer;
			int end = start + buffer;
			if(end>=args.size()) {
				end = args.size();
			}
			List<Object[]> batchArgs = args.subList(start, end);
			jt.batchUpdate(sql, batchArgs);
		}
	}
	
	/**
	 * 分页执行SQL
	 * @param jt 数据连接对象
	 * @param sql Sql语句
	 * @param datas 数据列表
	 * @param buffer 缓冲大小
	 */
	public static <T> void batchUpdate(JdbcTemplate jt, String sql, List<T> datas, int buffer) throws Exception {
		if(datas==null||datas.size()<=0) {
			return;
		}
		List<Object[]> args = new ArrayList<Object[]>();
		for(T data : datas) {
			args.add(SqlUtils.getObjectArgs(sql, data).toArray());
		}
		batchUpdateArr(jt, SqlUtils.getObjectSql(sql), args, buffer);
	}
	
	/**
	 * 分页执行SQL
	 * @param jt 数据连接对象
	 * @param sqls Sql语句列表
	 * @param buffer 缓冲大小
	 */
	public static void batchUpdate(JdbcTemplate jt, List<String> sqls, int buffer) throws Exception {
		if(sqls==null||sqls.size()<=0) {
			return;
		}
		int pageCount = (sqls.size()-1)/buffer+1;
		for(int i=0; i<pageCount; i++) {
			int start = i*buffer;
			int end = start + buffer;
			if(end>=sqls.size()) {
				end = sqls.size();
			}
			List<String> batchSqls = sqls.subList(start, end);
			jt.batchUpdate(batchSqls.toArray(new String[batchSqls.size()]));
		}
	}
	
}
