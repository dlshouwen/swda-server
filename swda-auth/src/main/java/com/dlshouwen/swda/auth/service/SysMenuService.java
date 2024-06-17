package com.dlshouwen.swda.auth.service;

import java.util.List;
import java.util.Set;

import com.dlshouwen.swda.auth.entity.SysMenuEntity;
import com.dlshouwen.swda.auth.vo.SysMenuVO;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.service.BaseService;


/**
 * 菜单管理
 * 
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

	/**
	 * 用户菜单列表
	 *
	 * @param user  用户
	 * @param type 菜单类型
	 */
	List<SysMenuVO> getUserMenuList(UserDetail user, Integer type);

	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserAuthority(UserDetail user);
}
