package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthUser;

import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.SysThirdLoginConvert;
import com.dlshouwen.swda.bms.mapper.SysThirdLoginDao;
import com.dlshouwen.swda.bms.entity.Auth;
import com.dlshouwen.swda.bms.service.SysThirdLoginService;
import com.dlshouwen.swda.bms.vo.AuthLoginVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * third login service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysThirdLoginServiceImpl extends BaseServiceImpl<SysThirdLoginDao, Auth> implements SysThirdLoginService {

	/**
	 * get third login vo list
	 * @param userId
	 * @return third login vo list
	 */
	@Override
	public List<AuthLoginVO> listByUserId(Long userId) {
//		get third login list
		List<Auth> list = baseMapper.selectList(Wrappers.<Auth>lambdaQuery().eq(Auth::getUserId, userId));
//		convert to third login vo list for return
		return SysThirdLoginConvert.INSTANCE.convertList(list);
	}

	/**
	 * unbind
	 * @param userId
	 * @param openType
	 */
	@Override
	public void unBind(Long userId, String openType) {
//		delete bind info
		baseMapper.delete(Wrappers.<Auth>lambdaQuery().eq(Auth::getUserId, userId).eq(Auth::getOpenType, openType));
	}

	/**
	 * bind
	 * @param userId
	 * @param openType
	 * @param authUser
	 */
	@Override
	public void bind(Long userId, String openType, AuthUser authUser) {
//		create third login
		Auth entity = new Auth();
//		set user id, opentype, open id, username
		entity.setUserId(userId);
		entity.setOpenType(openType);
		entity.setOpenId(authUser.getUuid());
		entity.setUsername(authUser.getUsername());
//		insert third login
		baseMapper.insert(entity);
	}

	/**
	 * get user id by open type and open id
	 * @param openType
	 * @param openId
	 * @return user id
	 */
	@Override
	public Long getUserIdByOpenTypeAndOpenId(String openType, String openId) {
//		get third login
		Auth entity = baseMapper.selectOne(Wrappers.<Auth>lambdaQuery().eq(Auth::getOpenType, openType).eq(Auth::getOpenId, openId));
//		if third login empty
		if (entity == null) {
//			throw exception
			throw new SwdaException("还未绑定用户，请先绑定用户");
		}
//		return user id
		return entity.getUserId();
	}

}