package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.utils.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysRoleEntity;
import com.dlshouwen.swda.bms.query.SysRoleQuery;
import com.dlshouwen.swda.bms.vo.SysRoleDataScopeVO;
import com.dlshouwen.swda.bms.vo.SysRoleVO;

import java.util.List;

/**
 * 角色
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysRoleService extends BaseService<SysRoleEntity> {

    PageResult<SysRoleVO> page(SysRoleQuery query);

    List<SysRoleVO> getList(SysRoleQuery query);

    void save(SysRoleVO vo);

    void update(SysRoleVO vo);

    void dataScope(SysRoleDataScopeVO vo);

    void delete(List<Long> idList);

    /**
     * 获取角色名称列表
     *
     * @param idList 角色ID列表
     * @return 角色名称列表
     */
    List<String> getNameList(List<Long> idList);
}
