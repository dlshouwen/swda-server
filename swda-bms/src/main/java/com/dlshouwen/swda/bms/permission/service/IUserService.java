package com.dlshouwen.swda.bms.permission.service;

import com.dlshouwen.swda.core.base.vo.LoginUserVO;
import com.dlshouwen.swda.core.base.vo.UserAvatarVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.permission.entity.User;
import com.dlshouwen.swda.bms.permission.vo.UserVO;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * user service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IUserService extends BaseService<User> {

	/**
	 * get user by mobile
	 * @param mobile
	 * @return user
	 */
	UserVO getUserByMobile(String mobile);

	/**
	 * update login user
	 * @param loginUserVO
	 */
	void updateLoginUser(LoginUserVO vo);

	/**
	 * update login user avatar
	 * @param userAvatarVO
	 */
	void updateLoginUserAvatar(UserAvatarVO avatar);

	/**
	 * update login user password
	 * @param userId
	 * @param password
	 */
	void updateLoginUserPassword(Long userId, String newPassword);

	/**
	 * get user page result
	 * @param query
	 * @return user page result
	 */
	PageResult<UserVO> getUserPageResult(Query<User> query);
	
	/**
	 * get user data
	 * @param userId
	 * @return user data
	 */
	UserVO getUserData(Long userId);

	/**
	 * add user
	 * @param userVO
	 */
	void addUser(UserVO vo);

	/**
	 * update update
	 * @param userVO
	 */
	void updateUser(UserVO vo);

	/**
	 * delete user
	 * @param userIdList
	 */
	void deleteUser(List<Long> userIdList);

	/**
	 * get user name list
	 * @param userIdList
	 * @return user name list
	 */
	List<String> getUserNameList(List<Long> userIdList);

	/**
	 * import user
	 * @param file
	 * @param password
	 */
	void importUser(MultipartFile file, String password);

	/**
	 * get role user list
	 * @param query
	 * @return page result
	 */
	PageResult<UserVO> getRoleUserList(Query<User> query);

}
