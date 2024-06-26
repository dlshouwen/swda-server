package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysPostEntity;
import com.dlshouwen.swda.bms.query.SysPostQuery;
import com.dlshouwen.swda.bms.vo.SysPostVO;

import java.util.List;

/**
 * 岗位管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysPostService extends BaseService<SysPostEntity> {

	PageResult<SysPostVO> page(SysPostQuery query);

	List<SysPostVO> getList();

	List<String> getNameList(List<Long> idList);

	void save(SysPostVO vo);

	void update(SysPostVO vo);

	void delete(List<Long> idList);
}