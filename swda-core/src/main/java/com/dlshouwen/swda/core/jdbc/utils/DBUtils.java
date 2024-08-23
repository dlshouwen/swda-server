package com.dlshouwen.swda.core.jdbc.utils;

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
 * db utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class DBUtils {

	/**
	 * get data source
	 * @param driverClass
	 * @param jdbc
	 * @param username
	 * @param password
	 * @return data source
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
	 * call
	 * @param template
	 * @param sql
	 * @param args
	 * @param returns
	 * @param types
	 * @return result
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
