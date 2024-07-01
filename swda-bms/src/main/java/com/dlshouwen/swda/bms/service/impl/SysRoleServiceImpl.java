package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.auth.enums.DataScopeEnum;
import com.dlshouwen.swda.bms.convert.SysRoleConvert;
import com.dlshouwen.swda.bms.mapper.SysRoleDao;
import com.dlshouwen.swda.bms.entity.Role;
import com.dlshouwen.swda.bms.query.SysRoleQuery;
import com.dlshouwen.swda.bms.service.*;
import com.dlshouwen.swda.bms.vo.RoleOrganVO;
import com.dlshouwen.swda.bms.vo.RoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * role service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, Role> implements SysRoleService {
	
	/** role menu service */
	private final SysRoleMenuService sysRoleMenuService;
	
	/** role data scope service */
	private final SysRoleDataScopeService sysRoleDataScopeService;
	
	/** user role service */
	private final SysUserRoleService sysUserRoleService;
	
	/** user token service */
	private final SysUserTokenService sysUserTokenService;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<RoleVO> page(SysRoleQuery query) {
//		select page
		IPage<Role> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(SysRoleConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get role list
	 * @param query
	 * @return role vo list
	 */
	@Override
	public List<RoleVO> getList(SysRoleQuery query) {
//		get role list
		List<Role> entityList = baseMapper.selectList(getWrapper(query));
//		convert to role vo list for result
		return SysRoleConvert.INSTANCE.convertList(entityList);
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private Wrapper<Role> getWrapper(SysRoleQuery query) {
//		create wrapper
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
//		set condition
		wrapper.like(StrUtil.isNotBlank(query.getName()), Role::getName, query.getName());
//		handle data scope wrapper
		dataScopeWrapper(wrapper);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param roleVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(RoleVO vo) {
//		convert to role
		Role entity = SysRoleConvert.INSTANCE.convert(vo);
//		set data scope
		entity.setDataScope(DataScopeEnum.SELF.getValue());
//		insert role
		baseMapper.insert(entity);
//		save role menu
		sysRoleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());
	}

	/**
	 * update
	 * @param roleVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(RoleVO vo) {
//		convert to role
		Role entity = SysRoleConvert.INSTANCE.convert(vo);
//		update role
		updateById(entity);
//		save role menu
		sysRoleMenuService.saveOrUpdate(entity.getId(), vo.getMenuIdList());
//		update auth cache
		sysUserTokenService.updateCacheAuthByRoleId(entity.getId());
	}

	/**
	 * data scope
	 * @param roleDataScopeVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void dataScope(RoleOrganVO vo) {
//		get role
		Role entity = getById(vo.getId());
//		set data scope
		entity.setDataScope(vo.getDataScope());
//		update role
		updateById(entity);
//		if custom data scope
		if (vo.getDataScope().equals(DataScopeEnum.CUSTOM.getValue())) {
//			update role data scope
			sysRoleDataScopeService.saveOrUpdate(entity.getId(), vo.getOrgIdList());
		} else {
//			delete role data scope
			sysRoleDataScopeService.deleteByRoleIdList(Collections.singletonList(vo.getId()));
		}
//		update auth cache
		sysUserTokenService.updateCacheAuthByRoleId(entity.getId());
	}

	/**
	 * delete
	 * @param idList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
//		dlete role list
		removeByIds(idList);
//		delete user role list
		sysUserRoleService.deleteByRoleIdList(idList);
//		delete role menu list
		sysRoleMenuService.deleteByRoleIdList(idList);
//		delete role data scope list
		sysRoleDataScopeService.deleteByRoleIdList(idList);
//		update auth cache
		idList.forEach(sysUserTokenService::updateCacheAuthByRoleId);
	}

	/**
	 * get role name list
	 * @param idList
	 * @return role name list
	 */
	@Override
	public List<String> getNameList(List<Long> idList) {
//		if id list is empty then return null
		if (idList.isEmpty()) {
			return null;
		}
//		get role name list for return
		return baseMapper.selectBatchIds(idList).stream().map(Role::getName).toList();
	}

}