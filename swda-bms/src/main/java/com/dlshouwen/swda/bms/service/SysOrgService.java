package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysOrgEntity;
import com.dlshouwen.swda.bms.vo.SysOrgVO;

import java.util.List;

/**
 * 机构管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
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