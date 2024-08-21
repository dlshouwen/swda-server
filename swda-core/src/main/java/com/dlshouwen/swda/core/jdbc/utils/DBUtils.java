package com.htz.core.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据库工具类
 * @author liujingcheng@live.cn
 * @since hygea 6.0
 */
public class DBUtils {
	
	/**
	 * 获取数据源
	 * <p>根据驱动类地址、链接地址、用户名及密码获取数据源信息，使用C3P0数据连接池
	 * @param driverClass 驱动类地址
	 * @param jdbcUrl 链接地址
	 * @param username 用户名
	 * @param password 密码
	 * @return 数据源对象
	 * @throws Exception 抛出全部异常
	 */
	public static DataSource getDataSource(String driverClass, String jdbcUrl, 
			String username, String password) throws Exception {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	/**
	 * 调用带返回值的存储过程
	 * @param template 数据连接对象
	 * @param sql sql语句
	 * @param args 参数
	 * @param returns 返回值位置
	 * @param types 返回值类型
	 * @return 返回值
	 */
	public static Object[] call(JdbcTemplate template, String sql, Object[] args, int[] returns, int[] types) {
		Object[] result = (Object[]) template.execute(new CallableStatementCreator() {
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall(sql);
				for (int i = 0; i < args.length; i++) {
					cs.setObject(i + 1, args[i]);
				}
				for (int i = 0; i < returns.length; i++) {
					cs.registerOutParameter(returns[i], types[i]);
				}
				return cs;
			}
		}, new CallableStatementCallback<Object>() {
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.execute();
				List<Object> rs = new ArrayList<Object>();
				for (int i : returns) {
					rs.add(cs.getObject(i));
				}
				return rs.toArray();
			}
		});
		return result;
	}
	
}
