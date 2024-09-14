package com.dlshouwen.swda.bms.log.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.bms.log.entity.EmailLog;
import com.dlshouwen.swda.bms.log.vo.EmailLogVO;

import java.util.List;

/**
 * email log convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmailLogConvert {
	
	/** instance */
    EmailLogConvert INSTANCE = Mappers.getMapper(EmailLogConvert.class);

    /**
     * convert
     * @param emailLogVO
     * @return email log
     */
    EmailLog convert(EmailLogVO vo);

    /**
     * convert to vo 
     * @param emailLog
     * @return email log vo
     */
    EmailLogVO convert2VO(EmailLog entity);

    /**
     * convert to vo list
     * @param emailLogList
     * @return email log vo list
     */
    List<EmailLogVO> convert2VOList(List<EmailLog> list);

}