package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysUserEntity;
import com.dlshouwen.swda.bms.query.SysRoleUserQuery;
import com.dlshouwen.swda.bms.query.SysUserQuery;
import com.dlshouwen.swda.bms.vo.SysUserAvatarVO;
import com.dlshouwen.swda.bms.vo.SysUserBaseVO;
import com.dlshouwen.swda.bms.vo.SysUserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * user service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysUserService extends BaseService<SysUserEntity> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<SysUserVO> page(SysUserQuery query);

	/**
	 * save
	 * @param userVO
	 */
	void save(SysUserVO vo);

	/**
	 * update
	 * @param userVO
	 */
	void update(SysUserVO vo);

	/**
	 * update login info
	 * @param userBaseVO
	 */
	void updateLoginInfo(SysUserBaseVO vo);

	/**
	 * update avatar
	 * @param userAvatarVO
	 */
	void updateAvatar(SysUserAvatarVO avatar);

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
	SysUserVO getByMobile(String mobile);

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
	PageResult<SysUserVO> roleUserPage(SysRoleUserQuery query);

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
