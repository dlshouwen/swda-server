package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.system.entity.Town;
import com.dlshouwen.swda.bms.system.vo.TownVO;

import java.util.List;

/**
 * town service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ITownService extends BaseService<Town> {

	/**
	 * get town page result
	 * @param query
	 * @return town page result
	 */
	PageResult<TownVO> getTownPageResult(Query<Town> query);

	/**
	 * get town data
	 * @param townId
	 * @return town data
	 */
	TownVO getTownData(Long townId);

	/**
	 * add town
	 * @param townVO
	 */
	void addTown(TownVO vo);

	/**
	 * update town
	 * @param townVO
	 */
	void updateTown(TownVO vo);

	/**
	 * delete town
	 * @param townIdList
	 */
	void deleteTown(List<Long> townIdList);

}
