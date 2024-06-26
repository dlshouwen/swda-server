package com.dlshouwen.swda.auth.service;

import me.zhyd.oauth.request.AuthRequest;

import java.util.List;

import com.dlshouwen.swda.auth.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.auth.vo.SysThirdLoginConfigVO;
import com.dlshouwen.swda.core.service.BaseService;

/**
 * third login config service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysThirdLoginConfigService extends BaseService<SysThirdLoginConfigEntity> {

	/**
	 * save
	 * @param thirdLoginConfigVO
	 */
	void save(SysThirdLoginConfigVO vo);

	/**
	 * update
	 * @param thirdLoginConfigVO
	 */
	void update(SysThirdLoginConfigVO vo);

	/**
	 * delete
	 * @param thirdLoginConfigIdList
	 */
	void delete(List<Long> idList);

	/**
	 * get auth request
	 * @param openType
	 * @return auth request
	 */
	AuthRequest getAuthRequest(String openType);

}