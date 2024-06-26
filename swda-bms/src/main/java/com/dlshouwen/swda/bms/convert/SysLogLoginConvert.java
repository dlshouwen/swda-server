package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysLogLoginEntity;
import com.dlshouwen.swda.bms.vo.SysLogLoginVO;
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
	SysLogLoginEntity convert(SysLogLoginVO vo);

	/**
	 * convert
	 * @param loginLog
	 * @return login log vo
	 */
	SysLogLoginVO convert(SysLogLoginEntity entity);

	/**
	 * convert list
	 * @param loginLogList
	 * @return login log vo list
	 */
	List<SysLogLoginVO> convertList(List<SysLogLoginEntity> list);

}