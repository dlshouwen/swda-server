package com.dlshouwen.swda.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * base dao
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class BaseDao extends JdbcTemplate {

	/**
	 * construct
	 * @param dataSource
	 */
	@Autowired
	public BaseDao(DataSource dataSource) {
		this.setDataSource(dataSource);
		this.afterPropertiesSet();
	}

	/**
	 * query for object
	 * @param sql
	 * @param requiredType
	 * @return T
	 */
	public <T> T queryForObject(@NonNull String sql, @NonNull Class<T> requiredType) {
		T result;
		try{
			result = super.queryForObject(sql, requiredType);
		}catch(EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param requiredType
	 * @param args
	 * @return T
	 */
	public <T> T queryForObject(@NonNull String sql, @NonNull Class<T> requiredType, @Nullable Object... args) {
		T result;
		try{
			result = super.queryForObject(sql, requiredType, args);
		}catch(EmptyResultDataAccessException exception) {
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
	 * @return T
	 */
	@Nullable
	public <T> T queryForObject(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argTypes, @NonNull Class<T> requiredType) {
		T result;
		try{
			result = super.queryForObject(sql, args, argTypes, requiredType);
		}catch(EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param rowMapper
	 * @return T
	 */
	@Nullable
	public <T> T queryForObject(@NonNull String sql, @NonNull RowMapper<T> rowMapper) {
		T result;
		try{
			result = super.queryForObject(sql, rowMapper);
		}catch(EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for object
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return T
	 */
	@Nullable
	public <T> T queryForObject(@NonNull String sql, @NonNull RowMapper<T> rowMapper, @Nullable Object... args) {
		T result;
		try{
			result = super.queryForObject(sql, rowMapper, args);
		}catch(EmptyResultDataAccessException exception) {
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
	 * @return T
	 */
	@Nullable
	public <T> T queryForObject(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argTypes, @NonNull RowMapper<T> rowMapper) {
		T result;
		try{
			result = super.queryForObject(sql, args, argTypes, rowMapper);
		}catch(EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for map
	 * @param sql
	 * @return map
	 */
	@Nullable
	public Map<String, Object> queryForMap(@NonNull String sql) {
		Map<String, Object> result;
		try{
			result = super.queryForMap(sql);
		}catch(EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for map
	 * @param sql
	 * @param args
	 * @return map
	 */
	@Nullable
	public Map<String, Object> queryForMap(@NonNull String sql, @Nullable Object... args) {
		Map<String, Object> result;
		try{
			result = super.queryForMap(sql, args);
		}catch(EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

	/**
	 * query for map
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @return map
	 */
	@Nullable
	public Map<String, Object> queryForMap(@NonNull String sql, @NonNull Object[] args, @NonNull int[] argTypes) {
		Map<String, Object> result;
		try{
			result = super.queryForMap(sql, args, argTypes);
		}catch(EmptyResultDataAccessException exception) {
			result = null;
		}
		return result;
	}

}
