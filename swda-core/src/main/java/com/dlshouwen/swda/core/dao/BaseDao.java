package com.dlshouwen.swda.core.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * base dao
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class BaseDao extends JdbcTemplate {

	/**
	 * constructor
	 * @param dataSource
	 */
	@Autowired
	public BaseDao(DataSource dataSource) {
//		set data source
		this.setDataSource(dataSource);
//		after properties set
		this.afterPropertiesSet();
	}

	/**
	 * query for obejct
	 * @param sql
	 * @param requiredType
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType) {
		T result = null;
		try {
			result = super.queryForObject(sql, requiredType);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
		T result = null;
		try {
			result = super.queryForObject(sql, requiredType, args);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @param requiredType
	 * @return
	 */
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType) {
		T result = null;
		try {
			result = super.queryForObject(sql, args, argTypes, requiredType);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
		T result = null;
		try {
			result = super.queryForObject(sql, rowMapper);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
		T result = null;
		try {
			result = super.queryForObject(sql, rowMapper, args);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) {
		T result = null;
		try {
			result = super.queryForObject(sql, args, argTypes, rowMapper);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for map
	 * @param sql
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql) {
		Map<String, Object> result = null;
		try {
			result = super.queryForMap(sql);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for map
	 * @param sql
	 * @param args
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, Object... args) {
		Map<String, Object> result = null;
		try {
			result = super.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for map
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) {
		Map<String, Object> result = null;
		try {
			result = super.queryForMap(sql, args, argTypes);
		} catch (EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

}
