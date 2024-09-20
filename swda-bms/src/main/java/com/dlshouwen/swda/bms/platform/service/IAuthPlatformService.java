package com.dlshouwen.swda.bms.platform.service;

import me.zhyd.oauth.request.AuthRequest;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.platform.entity.AuthPlatform;
import com.dlshouwen.swda.bms.platform.vo.AuthPlatformVO;

import java.util.List;

/**
 * auth platform service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IAuthPlatformService extends BaseService<AuthPlatform> {

	/**
	 * get auth platform page result
	 * @param query
	 * @return auth platform page result
	 */
	PageResult<AuthPlatformVO> getAuthPlatformPageResult(Query<AuthPlatform> query);

	/**
	 * get auth platform data
	 * @param authPlatformId
	 * @return auth platform
	 */
	AuthPlatformVO getAuthPlatformData(Long authPlatformId);

	/**
	 * add auth platform
	 * @param authPlatformVO
	 */
	void addAuthPlatform(AuthPlatformVO authPlatformVO);

	/**
	 * update auth platform
	 * @param authPlatformVO
	 */
	void updateAuthPlatform(AuthPlatformVO authPlatformVO);

	/**
	 * delete auth platform
	 * @param authPlatformIdList
	 */
	void deleteAuthPlatform(List<Long> authPlatformIdList);

	/**
	 * get auth request
	 * @param openType
	 * @return auth request
	 */
	AuthRequest getAuthRequest(Integer openType);

}