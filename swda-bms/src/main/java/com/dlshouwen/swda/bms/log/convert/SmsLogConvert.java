package com.dlshouwen.swda.bms.log.convert;

import net.maku.system.entity.SysSmsLogEntity;
import net.maku.system.vo.SysSmsLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信日志
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface SmsLogConvert {
    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    SmsLogVO convert(SysSmsLogEntity entity);

    List<SmsLogVO> convertList(List<SysSmsLogEntity> list);

}