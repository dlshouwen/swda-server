package com.dlshouwen.swda.bms.platform.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.bms.platform.vo.EmailPlatformVO;

import java.util.List;

/**
 * mail platform convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmailPlatformConvert {

	/** instance */
	EmailPlatformConvert INSTANCE = Mappers.getMapper(EmailPlatformConvert.class);

	/**
	 * convert
	 * @param emailPlatformVO
	 * @return email platform
	 */
	EmailPlatform convert(EmailPlatformVO vo);

	/**
	 * convert to vo
	 * @param emailPlatform
	 * @return email platform vo
	 */
	EmailPlatformVO convert2VO(EmailPlatform entity);

	/**
	 * convert list
	 * @param emailPlatformList
	 * @return email platform vo list
	 */
	List<EmailPlatform> convertList2(List<EmailPlatform> list);
	
	/**
	 * convert to vo list
	 * @param emailPlatformList
	 * @return email platform vo list
	 */
	List<EmailPlatformVO> convert2VOList(List<EmailPlatform> list);

}