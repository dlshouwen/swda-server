package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysLogLoginEntity;
import com.dlshouwen.swda.bms.vo.SysLogLoginVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 登录日志
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysLogLoginConvert {
	SysLogLoginConvert INSTANCE = Mappers.getMapper(SysLogLoginConvert.class);

	SysLogLoginEntity convert(SysLogLoginVO vo);

	SysLogLoginVO convert(SysLogLoginEntity entity);

	List<SysLogLoginVO> convertList(List<SysLogLoginEntity> list);

}