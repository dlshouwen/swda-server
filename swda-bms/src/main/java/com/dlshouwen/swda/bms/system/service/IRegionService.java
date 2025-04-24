package com.dlshouwen.swda.bms.system.service;

import com.dlshouwen.swda.bms.system.entity.Region;
import com.dlshouwen.swda.bms.system.vo.RegionVO;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

import java.util.List;

/**
 * region service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IRegionService extends BaseService<Region> {

	/**
	 * get region list
	 * @params regionId
	 * @return region vo list
	 */
	List<RegionVO> getRegionList(Integer regionId);
	
	/**
	 * get region data
	 * @param regionId
	 * @return region
	 */
	RegionVO getRegionData(Integer regionId);

	/**
	 * add region
	 * @param regionVO
	 */
	void addRegion(RegionVO regionVO);

	/**
	 * update region
	 * @param regionVO
	 */
	void updateRegion(RegionVO regionVO);

	/**
	 * delete region
	 * @param regionId
	 */
	void deleteRegion(Integer regionId);

	/**
	 * get sub region count
	 * @param preRegionId
	 * @return sub region count
	 */
	Long getSubRegionCount(Integer preRegionId);

}
