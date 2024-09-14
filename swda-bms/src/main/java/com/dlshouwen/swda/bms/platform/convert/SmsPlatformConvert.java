package com.dlshouwen.swda.bms.platform.convert;

import net.maku.sms.config.SmsConfig;
import net.maku.system.entity.SysSmsConfigEntity;
import net.maku.system.vo.SysSmsConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信配置
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface SmsPlatformConvert {
    SmsPlatformConvert INSTANCE = Mappers.getMapper(SmsPlatformConvert.class);

    SysSmsConfigEntity convert(SmsPlatformVO vo);

    SmsPlatformVO convert(SysSmsConfigEntity entity);

    List<SmsPlatformVO> convertList(List<SysSmsConfigEntity> list);

    SmsConfig convert2(SysSmsConfigEntity entity);

    List<SmsConfig> convertList2(List<SysSmsConfigEntity> list);

}