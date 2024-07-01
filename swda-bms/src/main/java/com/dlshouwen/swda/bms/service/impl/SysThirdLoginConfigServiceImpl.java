package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.*;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.query.Query;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.auth.enums.ThirdLoginEnum;
import com.dlshouwen.swda.bms.convert.SysThirdLoginConfigConvert;
import com.dlshouwen.swda.bms.mapper.SysThirdLoginConfigDao;
import com.dlshouwen.swda.bms.entity.AuthPlatform;
import com.dlshouwen.swda.bms.service.SysThirdLoginConfigService;
import com.dlshouwen.swda.bms.vo.AuthPlatformVO;
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
public class SysThirdLoginConfigServiceImpl extends BaseServiceImpl<SysThirdLoginConfigDao, AuthPlatform> implements SysThirdLoginConfigService {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<AuthPlatformVO> page(Query query) {
//		select page
		IPage<AuthPlatform> page = baseMapper.selectPage(getPage(query), Wrappers.lambdaQuery());
//		return page result
		return new PageResult<>(SysThirdLoginConfigConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * save
	 * @param thirdLoginConfigVO
	 */
	@Override
	public void save(AuthPlatformVO vo) {
//		convert to third login config
		AuthPlatform entity = SysThirdLoginConfigConvert.INSTANCE.convert(vo);
//		insert third login config
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param thirdLoginConfigVO
	 */
	@Override
	public void update(AuthPlatformVO vo) {
//		convert to third login config
		AuthPlatform entity = SysThirdLoginConfigConvert.INSTANCE.convert(vo);
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
//		delete third login config
		removeByIds(idList);
	}

	/**
	 * get auth request
	 * @param openType
	 * @return auth request
	 */
	@Override
	public AuthRequest getAuthRequest(String openType) {
//		get third login config
		AuthPlatform config = baseMapper.selectOne(new LambdaQueryWrapper<AuthPlatform>().eq(AuthPlatform::getOpenType, openType));
//		if config is null
		if (config == null) {
//			throw exception
			throw new SwdaException("未配置第三方登录，请配置后再尝试");
		}
//		get auth request
		AuthRequest authRequest = switch (ThirdLoginEnum.toEnum(openType)) {
			case WECHAT_WORK -> new AuthWeChatEnterpriseQrcodeRequest(
					AuthConfig.builder().clientId(config.getClientId()).clientSecret(config.getClientSecret())
						.redirectUri(config.getRedirectUri()).agentId(config.getAgentId()).build());
			case DING_TALK -> new AuthDingTalkRequest(
					AuthConfig.builder().clientId(config.getClientId())
						.clientSecret(config.getClientSecret()).redirectUri(config.getRedirectUri()).build());
			case FEI_SHU -> new AuthFeishuRequest(
					AuthConfig.builder().clientId(config.getClientId())
						.clientSecret(config.getClientSecret()).redirectUri(config.getRedirectUri()).build());
			case WECHAT_OPEN -> new AuthWeChatOpenRequest(
					AuthConfig.builder().clientId(config.getClientId())
						.clientSecret(config.getClientSecret()).redirectUri(config.getRedirectUri()).build());
		};
//		return auth request
		return authRequest;
	}

}