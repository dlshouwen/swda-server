package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthUser;

import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.SysThirdLoginConvert;
import com.dlshouwen.swda.bms.mapper.SysThirdLoginDao;
import com.dlshouwen.swda.bms.entity.SysThirdLoginEntity;
import com.dlshouwen.swda.bms.service.SysThirdLoginService;
import com.dlshouwen.swda.bms.vo.SysThirdLoginVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 第三方登录
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysThirdLoginServiceImpl extends BaseServiceImpl<SysThirdLoginDao, SysThirdLoginEntity>
		implements SysThirdLoginService {

	@Override
	public List<SysThirdLoginVO> listByUserId(Long userId) {
		List<SysThirdLoginEntity> list = baseMapper
				.selectList(Wrappers.<SysThirdLoginEntity>lambdaQuery().eq(SysThirdLoginEntity::getUserId, userId));

		return SysThirdLoginConvert.INSTANCE.convertList(list);
	}

	@Override
	public void unBind(Long userId, String openType) {
		baseMapper.delete(Wrappers.<SysThirdLoginEntity>lambdaQuery().eq(SysThirdLoginEntity::getUserId, userId)
				.eq(SysThirdLoginEntity::getOpenType, openType));
	}

	@Override
	public void bind(Long userId, String openType, AuthUser authUser) {
		SysThirdLoginEntity entity = new SysThirdLoginEntity();
		entity.setUserId(userId);
		entity.setOpenType(openType);
		entity.setOpenId(authUser.getUuid());
		entity.setUsername(authUser.getUsername());

		baseMapper.insert(entity);
	}

	@Override
	public Long getUserIdByOpenTypeAndOpenId(String openType, String openId) {
		SysThirdLoginEntity entity = baseMapper.selectOne(Wrappers.<SysThirdLoginEntity>lambdaQuery()
				.eq(SysThirdLoginEntity::getOpenType, openType).eq(SysThirdLoginEntity::getOpenId, openId));
		if (entity == null) {
			throw new SwdaException("还未绑定用户，请先绑定用户");
		}

		return entity.getUserId();
	}
}