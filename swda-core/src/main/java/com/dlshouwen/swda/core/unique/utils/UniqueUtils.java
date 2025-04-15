package com.dlshouwen.swda.core.unique.utils;

import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.jdbc.dao.BaseDao;

/**
 * unique utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class UniqueUtils {
	
	/**
	 * unique
	 * @param dao
	 * @param code
	 * @param args
	 * @return unique result
	 */
	public static boolean unique(BaseDao dao, String code, Object... args) {
//		get sql
		String sql = Data.unique.get(code);
//		get count
		int count = dao.queryForObject(sql, Integer.class, args);
//		return
		return count==0;
	}

}
