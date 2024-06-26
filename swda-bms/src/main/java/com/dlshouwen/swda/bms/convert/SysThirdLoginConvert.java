package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysThirdLoginEntity;
import com.dlshouwen.swda.bms.vo.SysThirdLoginVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 第三方登录
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysThirdLoginConvert {
	SysThirdLoginConvert INSTANCE = Mappers.getMapper(SysThirdLoginConvert.class);

	SysThirdLoginEntity convert(SysThirdLoginVO vo);

	SysThirdLoginVO convert(SysThirdLoginEntity entity);

	List<SysThirdLoginVO> convertList(List<SysThirdLoginEntity> list);

}