package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysThirdLoginConfigEntity;
import com.dlshouwen.swda.bms.vo.SysThirdLoginConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 第三方登录配置
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysThirdLoginConfigConvert {
	SysThirdLoginConfigConvert INSTANCE = Mappers.getMapper(SysThirdLoginConfigConvert.class);

	SysThirdLoginConfigEntity convert(SysThirdLoginConfigVO vo);

	SysThirdLoginConfigVO convert(SysThirdLoginConfigEntity entity);

	List<SysThirdLoginConfigVO> convertList(List<SysThirdLoginConfigEntity> list);

}