package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.LoginLog;
import com.dlshouwen.swda.bms.vo.LoginLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * login log convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysLogLoginConvert {
	
	/** instance */
	SysLogLoginConvert INSTANCE = Mappers.getMapper(SysLogLoginConvert.class);

	/**
	 * convert
	 * @param logonLogVO
	 * @return login log
	 */
	LoginLog convert(LoginLogVO vo);

	/**
	 * convert
	 * @param loginLog
	 * @return login log vo
	 */
	LoginLogVO convert(LoginLog entity);

	/**
	 * convert list
	 * @param loginLogList
	 * @return login log vo list
	 */
	List<LoginLogVO> convertList(List<LoginLog> list);

}