package com.dlshouwen.swda.bms.auth.convert;

import com.dlshouwen.swda.bms.auth.entity.AuthPlatform;
import com.dlshouwen.swda.bms.auth.vo.AuthPlatformVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * auth platform convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthPlatformConvert {
	
	/** instance */
	AuthPlatformConvert INSTANCE = Mappers.getMapper(AuthPlatformConvert.class);

	/**
	 * convert
	 * @param authPlatformVO
	 * @return auth platform
	 */
	AuthPlatform convert(AuthPlatformVO vo);

	/**
	 * convert to vo
	 * @param authPlatform
	 * @return auth platform vo
	 */
	AuthPlatformVO convert2VO(AuthPlatform entity);

	/**
	 * convert to vo list
	 * @param authPlatformList
	 * @return auth platform vo list
	 */
	List<AuthPlatformVO> convert2VOList(List<AuthPlatform> list);

}