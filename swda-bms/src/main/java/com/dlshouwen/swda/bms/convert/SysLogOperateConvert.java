package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysLogOperateEntity;
import com.dlshouwen.swda.bms.vo.SysLogOperateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 操作日志
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
@Mapper
public interface SysLogOperateConvert {
    SysLogOperateConvert INSTANCE = Mappers.getMapper(SysLogOperateConvert.class);

    SysLogOperateEntity convert(SysLogOperateVO vo);

    SysLogOperateVO convert(SysLogOperateEntity entity);

    List<SysLogOperateVO> convertList(List<SysLogOperateEntity> list);

}