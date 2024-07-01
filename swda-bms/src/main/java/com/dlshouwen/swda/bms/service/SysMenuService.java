package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Permission;
import com.dlshouwen.swda.bms.vo.PremissionVO;

import java.util.List;
import java.util.Set;

/**
 * menu service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysMenuService extends BaseService<Permission> {

	/**
	 * save
	 * @param menuVO
	 */
	void save(PremissionVO vo);

	/**
	 * update
	 * @param menuVO
	 */
	void update(PremissionVO vo);

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
	List<PremissionVO> getMenuList(Integer type);

	/**
	 * get user menu list
	 * @param user
	 * @param type
	 * @return menu vo list
	 */
	List<PremissionVO> getUserMenuList(UserDetail user, Integer type);

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
