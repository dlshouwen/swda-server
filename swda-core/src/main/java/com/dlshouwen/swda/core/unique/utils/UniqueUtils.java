package com.dlshouwen.swda.core.unique.utils;

import org.apache.commons.collections4.MapUtils;

import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.common.enums.ResultCode;
import com.dlshouwen.swda.core.jdbc.dao.BaseDao;
import com.dlshouwen.swda.core.unique.properties.UniqueProperties;

/**
 * 唯一验证工具类
 * @author liujingcheng@live.cn
 * @since hygea 6.0
 */
public class UniqueUtils {
	
	/**
	 * 验证唯一
	 * <p>用于进行表单唯一验证
	 * @param dao 数据库操作基类
	 * @param handler Ajax响应回执
	 * @param message 验证出错时的错误信息
	 * @param sqlCode SQL的对应键值
	 * @param args 参数列表，可变参数
	 * @return 验证唯一是否成功
	 * @throws Exception 抛出全部异常
	 */
	public static boolean unique(BaseDao dao, R<Boolean> r, String message, 
			String sqlCode, Object... args) throws Exception {
//		获取sql
		String sql = MapUtils.getString(UniqueProperties.UNIQUE, sqlCode);
//		获取参数列表
		int count = dao.queryForObject(sql, Integer.class, args);
//		处理结果级
		boolean validResult = count==0?true:false;
//		处理Ajax响应回执
		if(!validResult){
			r.setCode(ResultCode.INTERNAL_SERVER_ERROR.getCode());
			r.setMessage(message);
		}
		return validResult;
	}

}
