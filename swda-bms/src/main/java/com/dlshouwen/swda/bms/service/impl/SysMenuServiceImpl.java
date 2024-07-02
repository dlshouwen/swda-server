package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.utils.TreeUtils;
import com.dlshouwen.swda.core.dict.ZeroOne;
import com.dlshouwen.swda.core.entity.auth.UserDetail;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.PermissionConvert;
import com.dlshouwen.swda.bms.mapper.SysMenuDao;
import com.dlshouwen.swda.bms.entity.Permission;
import com.dlshouwen.swda.bms.service.SysMenuService;
import com.dlshouwen.swda.bms.service.SysRoleMenuService;
import com.dlshouwen.swda.bms.vo.PermissionVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * menu service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, Permission> implements SysMenuService {
	
	/** role menu service */
	private final SysRoleMenuService sysRoleMenuService;

	/**
	 * save
	 * @param menuVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(PermissionVO vo) {
//		convert to menu
		Permission entity = PermissionConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param menuVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(PermissionVO vo) {
//		convert to menu
		Permission entity = PermissionConvert.INSTANCE.convert(vo);
//		if menu id equals pre menu id
		if (entity.getId().equals(entity.getPid())) {
//			throw exception
			throw new SwdaException("上级菜单不能为自己");
		}
//		update
		updateById(entity);
	}

	/**
	 * delete
	 * @param id
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
//		delete menu
		removeById(id);
//		delete role menu
		// 删除角色菜单关系
		sysRoleMenuService.deleteByMenuId(id);
	}

	/**
	 * get menu list
	 * @param type
	 * @return menu list
	 */
	@Override
	public List<PermissionVO> getMenuList(Integer type) {
//		get menu list
		List<Permission> menuList = baseMapper.getMenuList(type);
//		build menu tree for return
		return TreeUtils.build(PermissionConvert.INSTANCE.convertList(menuList));
	}

	/**
	 * get user menu list
	 * @param user
	 * @param type
	 * @return menu vo list
	 */
	@Override
	public List<PermissionVO> getUserMenuList(UserDetail user, Integer type) {
//		defined menu list
		List<Permission> menuList;
//		if user is super admin
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
//			get menu list
			menuList = baseMapper.getMenuList(type);
		} else {
//			get user menu list
			menuList = baseMapper.getUserMenuList(user.getUserId(), type);
		}
//		build menu tree for return
		return TreeUtils.build(PermissionConvert.INSTANCE.convertList(menuList));
	}

	/**
	 * get sub menu count
	 * @param pid
	 * @return sub menu count
	 */
	@Override
	public Long getSubMenuCount(Long pid) {
		return count(new LambdaQueryWrapper<Permission>().eq(Permission::getPid, pid));
	}

	/**
	 * get user authority
	 * @param user
	 * @return user authority
	 */
	@Override
	public Set<String> getUserAuthority(UserDetail user) {
//		defined authority list
		List<String> authorityList;
//		if user is super admin
		if (user.getSuperAdmin().equals(ZeroOne.YES)) {
//			get authority list
			authorityList = baseMapper.getAuthorityList();
		} else {
//			get user authority list
			authorityList = baseMapper.getUserAuthorityList(user.getUserId());
		}
//		defined perms set
		Set<String> permsSet = new HashSet<>();
//		for each authority
		for (String authority : authorityList) {
//			blank then continue
			if (StrUtil.isBlank(authority)) {
				continue;
			}
//			add to perms
			permsSet.addAll(Arrays.asList(authority.trim().split(",")));
		}
//		return permission set
		return permsSet;
	}

}