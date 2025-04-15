package com.dlshouwen.swda.bms.app.convert;

import com.dlshouwen.swda.bms.app.entity.UserToken;
import com.dlshouwen.swda.bms.app.vo.UserTokenVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * user token convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTokenConvert {
	
	/** instance */
	UserTokenConvert INSTANCE = Mappers.getMapper(UserTokenConvert.class);

	/**
	 * convert
	 * @param userTokenVO
	 * @return user token
	 */
	UserToken convert(UserTokenVO vo);

	/**
	 * convert to vo
	 * @param userToken
	 * @return user token vo
	 */
	UserTokenVO convert2VO(UserToken entity);

}