package com.dlshouwen.swda.bms.service;

import me.zhyd.oauth.request.AuthRequest;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.query.Query;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.bms.vo.SysThirdLoginConfigVO;

import java.util.List;

/**
 * third login config service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysThirdLoginConfigService extends BaseService<SysThirdLoginConfigEntity> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<SysThirdLoginConfigVO> page(Query query);

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
	 * @param idList
	 */
	void delete(List<Long> idList);

	/**
	 * get auth request
	 * @param openType
	 * @return auth request
	 */
	AuthRequest getAuthRequest(String openType);

}