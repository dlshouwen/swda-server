package com.dlshouwen.swda.bms.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.AllArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.*;

import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.platform.convert.AuthPlatformConvert;
import com.dlshouwen.swda.bms.platform.dict.OpenType;
import com.dlshouwen.swda.bms.platform.entity.AuthPlatform;
import com.dlshouwen.swda.bms.platform.mapper.AuthPlatformMapper;
import com.dlshouwen.swda.bms.platform.service.IAuthPlatformService;
import com.dlshouwen.swda.bms.platform.vo.AuthPlatformVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * auth platform service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class AuthPlatformServiceImpl extends BaseServiceImpl<AuthPlatformMapper, AuthPlatform> implements IAuthPlatformService {

	/**
	 * get auth platform page result
	 * @param query
	 * @return auth platform page result
	 */
	@Override
	public PageResult<AuthPlatformVO> getAuthPlatformPageResult(Query<AuthPlatform> query) {
//		query page
		IPage<AuthPlatform> page = this.page(query);
//		convert to vo list for page return
		return new PageResult<>(AuthPlatformConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get auth platform data
	 * @param authPlatformId
	 * @return auth platform
	 */
	@Override
	public AuthPlatformVO getAuthPlatformData(Long authPlatformId) {
//		get auth platform
		AuthPlatform authPlatform = this.getById(authPlatformId);
//		convert to vo for return
		return AuthPlatformConvert.INSTANCE.convert2VO(authPlatform);
	}

	/**
	 * add auth platform
	 * @param authPlatformVO
	 */
	@Override
	public void addAuthPlatform(AuthPlatformVO authPlatformVO) {
//		convert to auth platform
		AuthPlatform authPlatform = AuthPlatformConvert.INSTANCE.convert(authPlatformVO);
//		insert auth platform
		this.save(authPlatform);
	}

	/**
	 * update auth platform
	 * @param authPlatformVO
	 */
	@Override
	public void updateAuthPlatform(AuthPlatformVO authPlatformVO) {
//		convert to auth platform
		AuthPlatform authPlatform = AuthPlatformConvert.INSTANCE.convert(authPlatformVO);
//		update auth platform
		this.updateById(authPlatform);
	}

	/**
	 * delete auth platform
	 * @param authPlatformIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAuthPlatform(List<Long> authPlatformIdList) {
//		delete auth platform
		this.removeByIds(authPlatformIdList);
	}

	/**
	 * get auth request
	 * @param openType
	 * @return auth request
	 */
	@Override
	public AuthRequest getAuthRequest(String openType) {
//		get auth platform
		AuthPlatform authPlatform = baseMapper.selectOne(Wrappers.<AuthPlatform>lambdaQuery().eq(AuthPlatform::getOpenType, openType));
//		if auth platform is null
		if (authPlatform == null) {
//			throw exception
			throw new SwdaException("未配置第三方登录，请配置后再尝试");
		}
//		get auth request
		AuthRequest authRequest = switch (openType) {
			case OpenType.WECHAT_OPEN -> new AuthWeChatOpenRequest(
					AuthConfig.builder().clientId(authPlatform.getClientId())
					.clientSecret(authPlatform.getClientSecret()).redirectUri(authPlatform.getRedirectUri()).build());
			case OpenType.WECHAT_WORK -> new AuthWeChatEnterpriseQrcodeRequest(
					AuthConfig.builder().clientId(authPlatform.getClientId()).clientSecret(authPlatform.getClientSecret())
						.redirectUri(authPlatform.getRedirectUri()).agentId(authPlatform.getAgentId()).build());
			case OpenType.DING_TALK -> new AuthDingTalkRequest(
					AuthConfig.builder().clientId(authPlatform.getClientId())
						.clientSecret(authPlatform.getClientSecret()).redirectUri(authPlatform.getRedirectUri()).build());
			case OpenType.FEI_SHU -> new AuthFeishuRequest(
					AuthConfig.builder().clientId(authPlatform.getClientId())
						.clientSecret(authPlatform.getClientSecret()).redirectUri(authPlatform.getRedirectUri()).build());
			default -> null;
		};
//		return auth request
		return authRequest;
	}

}