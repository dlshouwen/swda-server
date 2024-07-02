package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fhs.trans.service.impl.TransService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.dlshouwen.swda.core.utils.DateUtils;
import com.dlshouwen.swda.core.utils.ExcelUtils;
import com.dlshouwen.swda.core.utils.TokenUtils;
import com.dlshouwen.swda.core.cache.TokenCache;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.dict.ZeroOne;
import com.dlshouwen.swda.core.entity.auth.SecurityUser;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.excel.ExcelFinishCallBack;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.UserConvert;
import com.dlshouwen.swda.bms.mapper.UserMapper;
import com.dlshouwen.swda.bms.entity.User;
import com.dlshouwen.swda.bms.query.SysRoleUserQuery;
import com.dlshouwen.swda.bms.query.SysUserQuery;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.vo.UserAvatarVO;
import com.dlshouwen.swda.bms.vo.UserAssistVO;
import com.dlshouwen.swda.bms.vo.SysUserExcelVO;
import com.dlshouwen.swda.bms.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<UserMapper, User> implements SysUserService {
	
	/** user role service */
	private final SysUserRoleService sysUserRoleService;
	
	/** user post service */
	private final SysUserPostService sysUserPostService;
	
	/** user token service */
	private final SysUserTokenService sysUserTokenService;
	
	/** organ service */
	private final SysOrgService sysOrgService;
	
	/** token store cache */
	private final TokenCache tokenStoreCache;
	
	/** trans service */
	private final TransService transService;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<UserVO> page(SysUserQuery query) {
//		get params
		Map<String, Object> params = getParams(query);
//		get page
		IPage<User> page = getPage(query);
		params.put(Constant.PAGE, page);
//		get list
		List<User> list = baseMapper.getList(params);
//		return page result
		return new PageResult<>(UserConvert.INSTANCE.convertList(list), page.getTotal());
	}

	/**
	 * get params
	 * @param query
	 * @return params
	 */
	private Map<String, Object> getParams(SysUserQuery query) {
//		create params
		Map<String, Object> params = new HashMap<>();
//		put username, mobile, gender
		params.put("username", query.getUsername());
		params.put("mobile", query.getMobile());
		params.put("gender", query.getGender());
//		set data scope
		params.put(Constant.DATA_SCOPE, getDataScope("t1", null));
//		if has organ id
		if (query.getOrgId() != null) {
//			get sub organ id list
			List<Long> orgList = sysOrgService.getSubOrgIdList(query.getOrgId());
//			set organ id list
			params.put("orgList", orgList);
		}
//		return params
		return params;
	}

	/**
	 * save
	 * @param userVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(UserVO vo) {
//		convert to user
		User entity = UserConvert.INSTANCE.convert(vo);
//		set super admin
		entity.setSuperAdmin(ZeroOne.NO);
//		get user from username
		User user = baseMapper.getByUsername(entity.getUsername());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("用户名已经存在");
		}
//		get user from mobile
		user = baseMapper.getByMobile(entity.getMobile());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("手机号已经存在");
		}
//		insert user
		baseMapper.insert(entity);
//		set role list
		sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());
//		set post list
		sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
	}

	/**
	 * update
	 * @param userVO
	 */
	@Override
	public void update(UserVO vo) {
//		convert to user
		User entity = UserConvert.INSTANCE.convert(vo);
//		get user from username
		User user = baseMapper.getByUsername(entity.getUsername());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("用户名已经存在");
		}
//		get user from mobile
		user = baseMapper.getByMobile(entity.getMobile());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("手机号已经存在");
		}
//		update user
		updateById(entity);
//		update role list
		sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());
//		update post list
		sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
//		update cache auth
		sysUserTokenService.updateCacheAuthByUserId(entity.getId());
	}

	/**
	 * update login info
	 * @param userBaseVO
	 */
	@Override
	public void updateLoginInfo(UserAssistVO vo) {
//		convert to user
		User entity = UserConvert.INSTANCE.convert(vo);
//		set user id
		entity.setId(SecurityUser.getUserId());
//		get user from mobile
		User user = baseMapper.getByMobile(entity.getMobile());
//		if user is not null
		if (user != null && !user.getId().equals(entity.getId())) {
//			throw exception
			throw new SwdaException("手机号已经存在");
		}
//		update user
		updateById(entity);
//		delete user cache
		tokenStoreCache.deleteUser(TokenUtils.getAccessToken());
	}

	/**
	 * update avatar
	 * @param userAvatarVO
	 */
	@Override
	public void updateAvatar(UserAvatarVO avatar) {
//		create user
		User entity = new User();
//		set user id, avatar
		entity.setId(SecurityUser.getUserId());
		entity.setAvatar(avatar.getAvatar());
//		update user
		updateById(entity);
//		delete user cache
		tokenStoreCache.deleteUser(TokenUtils.getAccessToken());
	}

	/**
	 * delete
	 * @param idList
	 */
	@Override
	public void delete(List<Long> idList) {
//		delete user
		removeByIds(idList);
//		delete role list
		sysUserRoleService.deleteByUserIdList(idList);
//		delete post list
		sysUserPostService.deleteByUserIdList(idList);
	}

	/**
	 * get real name list
	 * @param idList
	 * @return name list
	 */
	@Override
	public List<String> getRealNameList(List<Long> idList) {
//		if id list is empty then return null
		if (idList.isEmpty()) {
			return null;
		}
//		select names to list from return
		return baseMapper.selectBatchIds(idList).stream().map(User::getRealName).toList();
	}

	/**
	 * get by mobile
	 * @param mobile
	 * @return userVO
	 */
	@Override
	public UserVO getByMobile(String mobile) {
//		get user by mobile
		User user = baseMapper.getByMobile(mobile);
//		convert to user vo
		return UserConvert.INSTANCE.convert(user);
	}

	/**
	 * update password
	 * @param id
	 * @param newPassword
	 */
	@Override
	public void updatePassword(Long id, String newPassword) {
//		get user
		User user = getById(id);
//		set password
		user.setPassword(newPassword);
//		update user
		updateById(user);
	}

	/**
	 * role user page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<UserVO> roleUserPage(SysRoleUserQuery query) {
//		get params
		Map<String, Object> params = getParams(query);
//		set role id
		params.put("roleId", query.getRoleId());
//		get page
		IPage<User> page = getPage(query);
		params.put(Constant.PAGE, page);
//		get role user list
		List<User> list = baseMapper.getRoleUserList(params);
//		return page result
		return new PageResult<>(UserConvert.INSTANCE.convertList(list), page.getTotal());
	}

	/**
	 * import by excel
	 * @param file
	 * @param password
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importByExcel(MultipartFile file, String password) {
//		analysis
		ExcelUtils.readAnalysis(file, SysUserExcelVO.class, new ExcelFinishCallBack<SysUserExcelVO>() {
			
			/**
			 * do after all analysed
			 * @param result
			 */
			@Override
			public void doAfterAllAnalysed(List<SysUserExcelVO> result) {
//				save user
				saveUser(result);
			}

			/**
			 * do save batch
			 * @param result
			 */
			@Override
			public void doSaveBatch(List<SysUserExcelVO> result) {
//				save user
				saveUser(result);
			}

			/**
			 * save user
			 * @param result
			 */
			private void saveUser(List<SysUserExcelVO> result) {
//				parse dict
				ExcelUtils.parseDict(result);
//				convert to user list
				List<User> sysUserEntities = UserConvert.INSTANCE.convertListEntity(result);
//				set password
				sysUserEntities.forEach(user -> user.setPassword(password));
//				batch save user
				saveBatch(sysUserEntities);
			}

		});

	}

	/**
	 * export
	 */
	@Override
	@SneakyThrows
	public void export() {
//		get user list
		List<User> list = list(Wrappers.lambdaQuery(User.class).eq(User::getSuperAdmin, ZeroOne.NO));
//		convert to user excel vo
		List<SysUserExcelVO> userExcelVOS = UserConvert.INSTANCE.convert2List(list);
//		trans batch
		transService.transBatch(userExcelVOS);
//		export excel
		ExcelUtils.excelExport(SysUserExcelVO.class, "system_user_excel" + DateUtils.format(new Date()), null, userExcelVOS);
	}

}
