package com.dlshouwen.swda.bms.log.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dlshouwen.swda.bms.log.entity.SmsLog;
import com.dlshouwen.swda.bms.log.vo.SmsLogVO;

import java.util.List;

/**
 * sms log convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SmsLogConvert {
	
	/** instance */
    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    /**
     * convert to vo
     * @param smsLog
     * @return sms log vo
     */
    SmsLogVO convert2VO(SmsLog entity);

    /**
     * convert to vo list
     * @param smsLogList
     * @return sms log vo list
     */
    List<SmsLogVO> convert2VOList(List<SmsLog> list);

}