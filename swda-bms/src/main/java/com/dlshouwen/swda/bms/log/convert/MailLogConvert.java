package com.dlshouwen.swda.bms.log.convert;

import net.maku.system.entity.SysMailLogEntity;
import net.maku.system.vo.SysMailLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 邮件日志
 *
 * @author 阿沐 babamu@126.com
 */
@Mapper
public interface MailLogConvert {
    MailLogConvert INSTANCE = Mappers.getMapper(MailLogConvert.class);

    MailLog convert(MailLogVO vo);

    MailLogVO convert(MailLog entity);

    List<MailLogVO> convertList(List<MailLog> list);

}