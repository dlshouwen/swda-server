package com.dlshouwen.swda.bms.platform.service;

import java.util.List;

import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.bms.platform.vo.SmsPlatformVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * sms platform service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ISmsPlatformService extends BaseService<SmsPlatform> {

	/**
	 * get sms platform page result
	 * @param query
	 * @return sms platform page result
	 */
	PageResult<SmsPlatformVO> getSmsPlatformPageResult(Query<SmsPlatform> query);

	/**
	 * get sms platform list
	 * @param smsPlatformType
	 * @return sms platform list
	 */
	List<SmsPlatformVO> getSmsPlatformList(Integer smsPlatformType);

	/**
	 * get enable sms platform list
	 * @return enable sms platform list
	 */
	List<SmsPlatform> getEnableSmsPlatformList();

	/**
	 * add sms platform
	 * @param smsPlatformVO
	 */
	void addSmsPlatform(SmsPlatformVO smsPlatformVO);

	/**
	 * update sms platform
	 * @param smsPlatformVO
	 */
	void updateSmsPlatform(SmsPlatformVO smsPlatformVO);

	/**
	 * delete sms platform
	 * @param smsPlatformIdList
	 */
	void deleteSmsPlatform(List<Long> smsPlatformIdList);

}