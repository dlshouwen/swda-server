package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.bms.system.convert.RegionConvert;
import com.dlshouwen.swda.bms.system.entity.Region;
import com.dlshouwen.swda.bms.system.mapper.RegionMapper;
import com.dlshouwen.swda.bms.system.service.IRegionService;
import com.dlshouwen.swda.bms.system.vo.RegionVO;
import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.base.utils.TreeUtils;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * region service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class RegionServiceImpl extends BaseServiceImpl<RegionMapper, Region> implements IRegionService {
	
	/**
	 * get region list
	 * @prarms regionId
	 * @return region list
	 */
	@Override
	public List<RegionVO> getRegionList(Integer regionId) {
//		get region list
		List<Region> regionList = baseMapper.getRegionList(regionId);
//		build region tree for return
		return TreeUtils.build(RegionConvert.INSTANCE.convert2VOList(regionList));
	}
	
	/**
	 * get region data
	 * @param regionId
	 * @return region data
	 */
	@Override
	public RegionVO getRegionData(Integer regionId) {
//		get region data
		Region region = this.getById(regionId);
//		convert to vo for return
		return RegionConvert.INSTANCE.convert2VO(region);
	}

	/**
	 * add region
	 * @param regionVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addRegion(RegionVO regionVO) {
//		convert to region
		Region region = RegionConvert.INSTANCE.convert(regionVO);
//		insert region
		this.save(region);
	}

	/**
	 * update region
	 * @param regionVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateRegion(RegionVO regionVO) {
//		convert to region
		Region region = RegionConvert.INSTANCE.convert(regionVO);
//		if region id equals pre region id
		if (region.getRegionId().equals(region.getPreRegionId())) {
//			throw exception
			throw new SwdaException("上级菜单不能为自己");
		}
//		update region
		this.updateById(region);
	}

	/**
	 * delete region
	 * @param regionId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRegion(Integer regionId) {
//		delete region
		this.removeById(regionId);
	}

	/**
	 * get sub region count
	 * @param preRegionId
	 * @return sub region count
	 */
	@Override
	public Long getSubRegionCount(Integer preRegionId) {
		return this.count(Wrappers.<Region>lambdaQuery().eq(Region::getPreRegionId, preRegionId));
	}

}