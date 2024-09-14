package com.dlshouwen.swda.bms.platform.convert;

import net.maku.email.config.EmailConfig;
import net.maku.system.entity.SysMailConfigEntity;
import net.maku.system.vo.SysMailConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 邮件配置
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface EmailPlatformConvert {
    EmailPlatformConvert INSTANCE = Mappers.getMapper(EmailPlatformConvert.class);

    SysMailConfigEntity convert(EmailPlatformVO vo);

    EmailPlatformVO convert(SysMailConfigEntity entity);

    List<EmailPlatformVO> convertList(List<SysMailConfigEntity> list);

    EmailPlatform convert2(SysMailConfigEntity entity);

    List<EmailPlatform> convertList2(List<SysMailConfigEntity> list);

}