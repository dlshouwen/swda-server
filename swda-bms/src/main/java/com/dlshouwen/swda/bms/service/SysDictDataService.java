package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysDictDataEntity;
import com.dlshouwen.swda.bms.query.SysDictDataQuery;
import com.dlshouwen.swda.bms.vo.SysDictDataVO;

import java.util.List;

/**
 * dict data service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysDictDataService extends BaseService<SysDictDataEntity> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<SysDictDataVO> page(SysDictDataQuery query);

	/**
	 * save
	 * @param dictVO
	 */
	void save(SysDictDataVO vo);

	/**
	 * update
	 * @param dictVO
	 */
	void update(SysDictDataVO vo);

	/**
	 * delete
	 * @param idList
	 */
	void delete(List<Long> idList);

}