package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysDictDataEntity;
import com.dlshouwen.swda.bms.query.SysDictDataQuery;
import com.dlshouwen.swda.bms.vo.SysDictDataVO;

import java.util.List;

/**
 * 数据字典
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysDictDataService extends BaseService<SysDictDataEntity> {

	PageResult<SysDictDataVO> page(SysDictDataQuery query);

	void save(SysDictDataVO vo);

	void update(SysDictDataVO vo);

	void delete(List<Long> idList);

}