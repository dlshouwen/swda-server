package com.dlshouwen.swda.bms.service;

import me.zhyd.oauth.request.AuthRequest;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.query.Query;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.bms.vo.SysThirdLoginConfigVO;

import java.util.List;

/**
 * 第三方登录配置
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysThirdLoginConfigService extends BaseService<SysThirdLoginConfigEntity> {

	PageResult<SysThirdLoginConfigVO> page(Query query);

	void save(SysThirdLoginConfigVO vo);

	void update(SysThirdLoginConfigVO vo);

	void delete(List<Long> idList);

	/**
	 * 根据类型，获取授权请求
	 *
	 * @param openType 第三方登录类型
	 */
	AuthRequest getAuthRequest(String openType);
}