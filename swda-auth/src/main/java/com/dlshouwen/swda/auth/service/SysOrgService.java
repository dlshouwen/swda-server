package com.dlshouwen.swda.auth.service;

import java.util.List;

import com.dlshouwen.swda.auth.entity.SysOrgEntity;
import com.dlshouwen.swda.auth.vo.SysOrgVO;
import com.dlshouwen.swda.core.service.BaseService;

/**
 * 机构管理
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public interface SysOrgService extends BaseService<SysOrgEntity> {

    List<SysOrgVO> getList();

    void save(SysOrgVO vo);

    void update(SysOrgVO vo);

    void delete(Long id);

    /**
     * 根据机构ID，获取子机构ID列表(包含本机构ID)
     *
     * @param id 机构ID
     */
    List<Long> getSubOrgIdList(Long id);

    /**
     * 根据机构ID列表，获取机构名称列表
     *
     * @param idList 机构ID列表
     */
    List<String> getNameList(List<Long> idList);
}