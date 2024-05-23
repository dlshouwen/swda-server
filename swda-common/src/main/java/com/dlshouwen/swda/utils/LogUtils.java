package com.dlshouwen.swda.utils;

import com.dlshouwen.swda.entity.log.DataLog;
import com.dlshouwen.swda.entity.log.LoginLog;
import com.dlshouwen.swda.entity.log.OperationLog;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * login utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class LogUtils {

	/** insert login log sql */
	private static final String insertLoginLogSQL = "insert into bms_login_log (log_id, token, user_id, user_name, organ_id, organ_name, login_time, ip, login_status, is_logout, logout_type, logout_time) values (${log_id }, ${token }, ${user_id }, ${user_name }, ${organ_id }, ${organ_name }, ${login_time }, ${ip }, ${login_status }, ${is_logout }, ${logout_type }, ${logout_time })";
	/** update login log sql */
	private static final String updateLoginLogSQL = "update bms_login_log set is_logout='1', logout_type=?, logout_time=? where log_id=?";
	/** insert operation log sql */
	private static final String insertOperationLogSQL = "insert into bms_operation_log (log_id, call_source, operation_url, operation_type, operation_result, error_reason, operation_detail, response_start, response_end, cost, user_id, user_name, organ_id, organ_name, ip) values (${log_id }, ${call_source }, ${operation_url }, ${operation_type }, ${operation_result }, ${error_reason }, ${operation_detail }, ${response_start }, ${response_end }, ${cost }, ${user_id }, ${user_name }, ${organ_id }, ${organ_name }, ${ip })";
	/** insert data log sql */
	private static final String insertDataLogSQL = "insert into bms_data_log (log_id, call_type, call_source, line_no, operation_type, operation_sql, params, call_result, error_reason, execute_type, execute_result, result_type, start_time, end_time, cost, user_id, user_name, organ_id, organ_name, ip) values (${log_id }, ${call_type }, ${call_source }, ${line_no }, ${operation_type }, ${operation_sql }, ${params }, ${call_result }, ${error_reason }, ${execute_type }, ${execute_result }, ${result_type }, ${start_time }, ${end_time }, ${cost }, ${user_id }, ${user_name }, ${organ_id }, ${organ_name }, ${ip })";

	/**
	 * insert login log
	 * @param template
	 * @param loginLog
	 * @return count
	 */
	public static int insertLoginLog(JdbcTemplate template, LoginLog loginLog){
		return template.update(SqlUtils.getObjectSql(insertLoginLogSQL), SqlUtils.getObjectArgs(insertLoginLogSQL, loginLog).toArray());
	}

	/**
	 * update login log
	 * @param template
	 * @param logId
	 * @param logoutType
	 * @return count
	 */
	public static int updateLoginLog(JdbcTemplate template, Long logId, String logoutType){
		return template.update(updateLoginLogSQL, logoutType, new Date(), logId);
	}

	/**
	 * insert operation log
	 * @param template
	 * @param operationLogList
	 * @return count
	 */
	public static int[] insertOperationLog(JdbcTemplate template, List<OperationLog> operationLogList){
		return PageUtils.batchUpdate(template, insertOperationLogSQL, operationLogList, 500);
	}

	/**
	 * insert data log
	 * @param template
	 * @param dataLogList
	 * @return count
	 */
	public static int[] insertDataLog(JdbcTemplate template, List<DataLog> dataLogList){
		return PageUtils.batchUpdate(template, insertDataLogSQL, dataLogList, 500);
	}

}
