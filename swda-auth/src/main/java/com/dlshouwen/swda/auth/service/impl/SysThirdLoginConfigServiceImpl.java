package com.dlshouwen.swda.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlshouwen.swda.auth.convert.SysThirdLoginConfigConvert;
import com.dlshouwen.swda.auth.dao.SysThirdLoginConfigDao;
import com.dlshouwen.swda.auth.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.auth.enums.ThirdLoginEnum;
import com.dlshouwen.swda.auth.service.SysThirdLoginConfigService;
import com.dlshouwen.swda.auth.vo.SysThirdLoginConfigVO;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * third login config service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysThirdLoginConfigServiceImpl extends BaseServiceImpl<SysThirdLoginConfigDao, SysThirdLoginConfigEntity>
		implements SysThirdLoginConfigService {

	/**
	 * save
	 * @param thirdLoginConfigVO
	 */
	@Override
	public void save(SysThirdLoginConfigVO vo) {
//		convert to third login config
		SysThirdLoginConfigEntity entity = SysThirdLoginConfigConvert.INSTANCE.convert(vo);
//		insert third login config
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param thirdLoginConfigVO
	 */
	@Override
	public void update(SysThirdLoginConfigVO vo) {
//		convert to third login config
		SysThirdLoginConfigEntity entity = SysThirdLoginConfigConvert.INSTANCE.convert(vo);
//		update third login config
		updateById(entity);
	}

	/**
	 * delete
	 * @param idList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
//		delete third login config list
		removeByIds(idList);
	}

	/**
	 * get auth request
	 * @param openType
	 */
	@Override
	public AuthRequest getAuthRequest(String openType) {
//		get third login config
		SysThirdLoginConfigEntity config = baseMapper.selectOne(new LambdaQueryWrapper<SysThirdLoginConfigEntity>().eq(SysThirdLoginConfigEntity::getOpenType, openType));
//		if third login config is null
		if (config == null) {
//			throw exception
			throw new SwdaException("未配置第三方登录，请配置后再尝试");
		}
//		create auth request
		AuthRequest authRequest = switch (ThirdLoginEnum.toEnum(openType)) {
			case WECHAT_WORK -> new AuthWeChatEnterpriseQrcodeRequest(
					AuthConfig.builder().clientId(config.getClientId()).clientSecret(config.getClientSecret()).redirectUri(config.getRedirectUri()).agentId(config.getAgentId()).build());
			case DING_TALK -> new AuthDingTalkRequest(
					AuthConfig.builder().clientId(config.getClientId()).clientSecret(config.getClientSecret()).redirectUri(config.getRedirectUri()).build());
			case FEI_SHU -> new AuthFeishuRequest(
					AuthConfig.builder().clientId(config.getClientId()).clientSecret(config.getClientSecret()).redirectUri(config.getRedirectUri()).build());
			case WECHAT_OPEN -> new AuthWeChatOpenRequest(
					AuthConfig.builder().clientId(config.getClientId()).clientSecret(config.getClientSecret()).redirectUri(config.getRedirectUri()).build());
		};
//		return auth reuqest
		return authRequest;
	}

}