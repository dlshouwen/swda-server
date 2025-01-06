package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.dict.ZeroOne;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.excel.callback.ExcelFinishCallBack;
import com.dlshouwen.swda.core.excel.utils.ExcelUtils;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.security.cache.TokenCache;
import com.dlshouwen.swda.core.security.user.SecurityUser;
import com.dlshouwen.swda.core.security.utils.TokenUtils;
import com.dlshouwen.swda.bms.app.service.IUserTokenService;
import com.dlshouwen.swda.bms.system.convert.UserConvert;
import com.dlshouwen.swda.bms.system.entity.User;
import com.dlshouwen.swda.bms.system.mapper.UserMapper;
import com.dlshouwen.swda.bms.system.service.IUserPostService;
import com.dlshouwen.swda.bms.system.service.IUserRoleService;
import com.dlshouwen.swda.bms.system.service.IUserService;
import com.dlshouwen.swda.bms.system.vo.LoginUserVO;
import com.dlshouwen.swda.bms.system.vo.UserAvatarVO;
import com.dlshouwen.swda.bms.system.vo.UserExcelVO;
import com.dlshouwen.swda.bms.system.vo.UserVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * user service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	
	/** user role service */
	private final IUserRoleService userRoleService;
	
	/** user post service */
	private final IUserPostService userPostService;
	
	/** user token service */
	private final IUserTokenService userTokenService;
	
	/** token store cache */
	private final TokenCache tokenCache;

	/**
	 * get user by mobile
	 * @param mobile
	 * @return userVO
	 */
	@Override
	public UserVO getUserByMobile(String mobile) {
//		get user by mobile
		User user = baseMapper.getUserByMobile(mobile);
//		convert to user vo
		return UserConvert.INSTANCE.convert2VO(user);
	}

	/**
	 * update login user
	 * @param loginUserVO
	 */
	@Override
	public void updateLoginUser(LoginUserVO loginUserVO) {
//		get user from mobile
		User user = baseMapper.getUserByMobile(loginUserVO.getMobile());
//		if user is not null
		if (user != null && !user.getUserId().equals(loginUserVO.getUserId())) {
//			throw exception
			throw new SwdaException("手机号已经存在");
		}
//		convert to user
		user = UserConvert.INSTANCE.convert(loginUserVO);
//		set user id
		user.setUserId(SecurityUser.getUserId());
//		update user
		this.updateById(user);
//		delete user cache
		tokenCache.deleteUser(TokenUtils.getAccessToken());
	}

	/**
	 * update login user avatar
	 * @param userAvatarVO
	 */
	@Override
	public void updateLoginUserAvatar(UserAvatarVO userAvatarVO) {
//		create user
		User user = new User();
//		set user id, avatar
		user.setUserId(SecurityUser.getUserId());
		user.setAvatar(userAvatarVO.getAvatar());
//		update user
		this.updateById(user);
//		delete user cache
		tokenCache.deleteUser(TokenUtils.getAccessToken());
	}

	/**
	 * update login user password
	 * @param userId
	 * @param password
	 */
	@Override
	public void updateLoginUserPassword(Long userId, String password) {
//		get user
		User user = this.getById(userId);
//		set password
		user.setPassword(password);
//		update user
		this.updateById(user);
	}
	
	/**
	 * get user page result
	 * @param query
	 * @return user page result
	 */
	@Override
	public PageResult<UserVO> getUserPageResult(Query<User> query) {
//		query page
		IPage<User> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(UserConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get user data
	 * @param userId
	 * @return user data
	 */
	@Override
	public UserVO getUserData(Long userId) {
//		get user
		User user = this.getById(userId);
//		convert to vo for return
		return UserConvert.INSTANCE.convert2VO(user);
	}

	/**
	 * add user
	 * @param userVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addUser(UserVO userVO) {
//		get user from username
		User user = baseMapper.getUserByUsername(userVO.getUserCode());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("用户名已经存在");
		}
//		get user from mobile
		user = baseMapper.getUserByMobile(userVO.getMobile());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("手机号已经存在");
		}
//		convert to user
		user = UserConvert.INSTANCE.convert(userVO);
//		set super admin
		user.setSuperAdmin(ZeroOne.NO);
//		insert user
		this.save(user);
//		set role list
		userRoleService.saveOrUpdate(user.getUserId(), userVO.getRoleIdList());
//		set post list
		userPostService.saveOrUpdate(user.getUserId(), userVO.getPostIdList());
	}

	/**
	 * update user
	 * @param userVO
	 */
	@Override
	public void updateUser(UserVO userVO) {
//		get user from username
		User user = baseMapper.getUserByUsername(userVO.getUserName());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("用户名已经存在");
		}
//		get user from mobile
		user = baseMapper.getUserByMobile(userVO.getMobile());
//		if user is not null
		if (user != null) {
//			throw exception
			throw new SwdaException("手机号已经存在");
		}
//		convert to user
		user = UserConvert.INSTANCE.convert(userVO);
//		update user
		this.updateById(user);
//		update role list
		userRoleService.saveOrUpdate(user.getUserId(), userVO.getRoleIdList());
//		update post list
		userPostService.saveOrUpdate(user.getUserId(), userVO.getPostIdList());
//		update user cache
		userTokenService.updateUserCacheByUserId(user.getUserId());
	}

	/**
	 * delete user
	 * @param userIdList
	 */
	@Override
	public void deleteUser(List<Long> userIdList) {
//		delete user
		this.removeByIds(userIdList);
//		delete role list
		userRoleService.deleteUserRoleByUserIdList(userIdList);
//		delete post list
		userPostService.deleteUserPostByUserIdList(userIdList);
	}

	/**
	 * get user name list
	 * @param userIdList
	 * @return name list
	 */
	@Override
	public List<String> getUserNameList(List<Long> userIdList) {
//		if id list is empty then return null
		if (userIdList.isEmpty()) {
			return null;
		}
//		select names to list from return
		return this.listByIds(userIdList).stream().map(User::getRealName).toList();
	}

	/**
	 * import user
	 * @param file
	 * @param password
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importUser(MultipartFile file, String password) {
//		analysis
		ExcelUtils.readAnalysis(file, UserExcelVO.class, new ExcelFinishCallBack<UserExcelVO>() {
			
			/**
			 * do after all analysed
			 * @param result
			 */
			@Override
			public void doAfterAllAnalysed(List<UserExcelVO> result) {
//				save user
				saveUser(result);
			}

			/**
			 * do save batch
			 * @param result
			 */
			@Override
			public void doSaveBatch(List<UserExcelVO> result) {
//				save user
				saveUser(result);
			}

			/**
			 * save user
			 * @param result
			 */
			private void saveUser(List<UserExcelVO> result) {
//				parse dict
				ExcelUtils.parseDict(result);
//				convert to user list
				List<User> userList = UserConvert.INSTANCE.convertList(result);
//				set password
				userList.forEach(user -> user.setPassword(password));
//				batch save user
				saveBatch(userList);
			}

		});

	}

	/**
	 * get role user list
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<UserVO> getRoleUserList(Query<User> query) {
//		get page
		IPage<User> page = this.page(query);
//		return page result
		return new PageResult<>(UserConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

}
