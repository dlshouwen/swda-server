package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysMenuEntity;
import com.dlshouwen.swda.bms.vo.SysMenuVO;

import java.util.List;
import java.util.Set;

/**
 * menu service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

	/**
	 * save
	 * @param menuVO
	 */
	void save(SysMenuVO vo);

	/**
	 * update
	 * @param menuVO
	 */
	void update(SysMenuVO vo);

	/**
	 * delete
	 * @param id
	 */
	void delete(Long id);

	/**
	 * get menu list
	 * @param type
	 * @return menu vo list
	 */
	List<SysMenuVO> getMenuList(Integer type);

	/**
	 * get user menu list
	 * @param user
	 * @param type
	 * @return menu vo list
	 */
	List<SysMenuVO> getUserMenuList(UserDetail user, Integer type);

	/**
	 * get sub menu count
	 * @param pid
	 * @return sub menu count
	 */
	Long getSubMenuCount(Long pid);

	/**
	 * get user authority
	 * @param user
	 * @return user authority
	 */
	Set<String> getUserAuthority(UserDetail user);

}
