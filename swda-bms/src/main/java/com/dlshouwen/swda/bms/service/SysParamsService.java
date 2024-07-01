package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Attr;
import com.dlshouwen.swda.bms.query.SysParamsQuery;
import com.dlshouwen.swda.bms.vo.AttrVO;

import java.util.List;

/**
 * params service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysParamsService extends BaseService<Attr> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<AttrVO> page(SysParamsQuery query);

	/**
	 * save
	 * @param paramsVO
	 */
	void save(AttrVO vo);

	/**
	 * update
	 * @param paramsVO
	 */
	void update(AttrVO vo);

	/**
	 * delete
	 * @param idList
	 */
	void delete(List<Long> idList);

	/**
	 * get string
	 * @param paramKey
	 * @return value
	 */
	String getString(String paramKey);

	/**
	 * get int
	 * @param paramKey
	 * @return value
	 */
	int getInt(String paramKey);

	/**
	 * get boolean
	 * @param paramKey
	 * @return value
	 */
	boolean getBoolean(String paramKey);

	/**
	 * get json object
	 * @param paramKey 
	 * @param valueType
	 * @return json object
	 */
	<T> T getJSONObject(String paramKey, Class<T> valueType);

}