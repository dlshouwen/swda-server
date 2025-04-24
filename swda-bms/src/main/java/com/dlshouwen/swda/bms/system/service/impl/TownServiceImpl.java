package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.system.convert.TownConvert;
import com.dlshouwen.swda.bms.system.entity.Town;
import com.dlshouwen.swda.bms.system.mapper.TownMapper;
import com.dlshouwen.swda.bms.system.service.ITownService;
import com.dlshouwen.swda.bms.system.vo.TownVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * town service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class TownServiceImpl extends BaseServiceImpl<TownMapper, Town> implements ITownService {

	/**
	 * get town page result
	 * @param query
	 * @return town page result
	 */
	@Override
	public PageResult<TownVO> getTownPageResult(Query<Town> query) {
//		query page
		IPage<Town> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(TownConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get town data
	 * @param townId
	 * @return town data
	 */
	@Override
	public TownVO getTownData(Long townId) {
//		get town data
		Town town = this.getById(townId);
//		convert to town vo for return
		return TownConvert.INSTANCE.convert2VO(town);
	}

	/**
	 * add town
	 * @param townVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addTown(TownVO townVO) {
//		convert to town
		Town town = TownConvert.INSTANCE.convert(townVO);
//		insert town
		this.save(town);
	}

	/**
	 * update town
	 * @param townVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateTown(TownVO townVO) {
//		convert to town
		Town town = TownConvert.INSTANCE.convert(townVO);
//		update town
		this.updateById(town);
	}

	/**
	 * delete town
	 * @param townIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTown(List<Long> townIdList) {
//		dlete town list
		this.removeByIds(townIdList);
	}

}