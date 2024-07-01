package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.User;
import com.dlshouwen.swda.bms.query.SysRoleUserQuery;
import com.dlshouwen.swda.bms.query.SysUserQuery;
import com.dlshouwen.swda.bms.vo.UserAvatarVO;
import com.dlshouwen.swda.bms.vo.UserAssistVO;
import com.dlshouwen.swda.bms.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * user service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysUserService extends BaseService<User> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<UserVO> page(SysUserQuery query);

	/**
	 * save
	 * @param userVO
	 */
	void save(UserVO vo);

	/**
	 * update
	 * @param userVO
	 */
	void update(UserVO vo);

	/**
	 * update login info
	 * @param userBaseVO
	 */
	void updateLoginInfo(UserAssistVO vo);

	/**
	 * update avatar
	 * @param userAvatarVO
	 */
	void updateAvatar(UserAvatarVO avatar);

	/**
	 * delete
	 * @param idList
	 */
	void delete(List<Long> idList);

	/**
	 * get real name list
	 * @param idList
	 * @return real name list
	 */
	List<String> getRealNameList(List<Long> idList);

	/**
	 * get user by mobile
	 * @param mobile
	 * @return user
	 */
	UserVO getByMobile(String mobile);

	/**
	 * update password
	 * @param id
	 * @param newPassword
	 */
	void updatePassword(Long id, String newPassword);

	/**
	 * role user page
	 * @param query
	 * @return page result
	 */
	PageResult<UserVO> roleUserPage(SysRoleUserQuery query);

	/**
	 * import by excel
	 * @param file
	 * @param password
	 */
	void importByExcel(MultipartFile file, String password);

	/**
	 * export
	 */
	void export();

}
