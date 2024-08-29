package com.dlshouwen.swda.bms.log.convert;

import com.dlshouwen.swda.bms.log.vo.LoginLogVO;
import com.dlshouwen.swda.core.log.entity.LoginLog;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * login log convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginLogConvert {
	
	/** instance */
	LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

	/**
	 * convert
	 * @param logonLogVO
	 * @return login log
	 */
	LoginLog convert(LoginLogVO vo);

	/**
	 * convert to vo
	 * @param loginLog
	 * @return login log vo
	 */
	LoginLogVO convert2VO(LoginLog entity);

	/**
	 * convert to vo list
	 * @param loginLogList
	 * @return login log vo list
	 */
	List<LoginLogVO> convert2VOList(List<LoginLog> list);

}