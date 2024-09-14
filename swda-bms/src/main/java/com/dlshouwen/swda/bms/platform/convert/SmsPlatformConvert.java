package com.dlshouwen.swda.bms.platform.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.bms.platform.vo.SmsPlatformVO;

import java.util.List;

/**
 * sms platform convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SmsPlatformConvert {

	/** instance */
	SmsPlatformConvert INSTANCE = Mappers.getMapper(SmsPlatformConvert.class);

	/**
	 * convert
	 * @param smsPlatformVO
	 * @return sms platform
	 */
	SmsPlatform convert(SmsPlatformVO vo);

	/**
	 * convert to vo
	 * @param smsPlatform
	 * @return sms platform vo
	 */
	SmsPlatformVO convert(SmsPlatform entity);

	/**
	 * convert to vo list
	 * @param smsPlatformList
	 * @return sms platform vo list
	 */
	List<SmsPlatformVO> convert2VOList(List<SmsPlatform> list);

}