package com.dlshouwen.swda.bms.system.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.cache.annotation.Cacheable;

import com.dlshouwen.swda.bms.system.entity.Region;
import com.dlshouwen.swda.bms.system.provider.RegionProvider;
import com.dlshouwen.swda.core.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * region mapper
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface RegionMapper extends BaseMapper<Region> {
	
	@Cacheable(cacheNames = "com.dlshouwen.swda:bms:system.region", key = "#root.methodName+#regionId")
	@SelectProvider(type = RegionProvider.class, method = "getRedisData")
	Region getRedisData(Integer regionId);
	
	/**
	 * get region list
	 * @prarms regionId
	 * @return region list
	 */
	@Select("select r.*, (select count(*) from bms_region where pre_region_id=r.region_id and deleted=0) hasChildren from bms_region r where r.pre_region_id=#{regionId} and r.deleted=0")
	List<Region> getRegionList(Integer regionId);

}
