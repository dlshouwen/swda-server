package com.dlshouwen.swda.auth.service;

import java.util.List;
import java.util.Set;

import com.dlshouwen.swda.auth.entity.SysMenuEntity;
import com.dlshouwen.swda.auth.vo.SysMenuVO;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.service.BaseService;

/**
 * menu service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

	/**
	 * get user menu list
	 * @param user
	 * @param type
	 * @return menu list
	 */
	List<SysMenuVO> getUserMenuList(UserDetail user, Integer type);

	/**
	 * get user authority
	 * @param userDetail
	 * @return user authority set
	 */
	Set<String> getUserAuthority(UserDetail user);
	
}
