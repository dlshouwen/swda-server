package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.core.security.user.UserDetail;
import com.dlshouwen.swda.bms.system.entity.Permission;
import com.dlshouwen.swda.bms.system.vo.PermissionVO;

import java.util.List;
import java.util.Set;

/**
 * permission service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IPermissionService extends BaseService<Permission> {

	/**
	 * get user menu list
	 * @param user
	 * @param permissionType
	 * @return permission vo list
	 */
	List<PermissionVO> getUserMenuList(UserDetail user, Integer permissionType);
	
	/**
	 * get user authority list
	 * @param user
	 * @return user authority list
	 */
	Set<String> getUserAuthorityList(UserDetail user);

	/**
	 * get permission list
	 * @param permissionType
	 * @return permission vo list
	 */
	List<PermissionVO> getPermissionList(Integer permissionType);
	
	/**
	 * get permission data
	 * @param permissionId
	 * @return permission
	 */
	PermissionVO getPermissionData(Long permissionId);

	/**
	 * add permission
	 * @param permissionVO
	 */
	void addPermission(PermissionVO permissionVO);

	/**
	 * update permission
	 * @param permissionVO
	 */
	void updatePermission(PermissionVO permissionVO);

	/**
	 * delete permission
	 * @param permissionId
	 */
	void deletePermission(Long permissionId);

	/**
	 * get sub permission count
	 * @param prePermissionId
	 * @return sub permission count
	 */
	Long getSubPermissionCount(Long prePermissionId);

}
