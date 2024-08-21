package com.dlshouwen.swda.bms.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dlshouwen.swda.bms.sms.cache.SmsCache;
import com.dlshouwen.swda.bms.sms.entity.SmsPlatform;
import com.dlshouwen.swda.bms.sms.mapper.SmsPlatformMapper;
import com.dlshouwen.swda.bms.sms.service.SmsPlatformService;
import com.dlshouwen.swda.core.common.dict.OpenClose;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sms platform service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SmsPlatformServiceImpl extends BaseServiceImpl<SmsPlatformMapper, SmsPlatform> implements SmsPlatformService {

	/** sms cache */
	private final SmsCache smsCache;

	/**
	 * get enabled platform list
	 * @return enabled platform list
	 */
	@Override
	public List<SmsPlatform> getEnabledPlatformList() {
//		get platform list from cache
		List<SmsPlatform> platformList = smsCache.getPlatformlist();
//		if null
		if (platformList == null) {
//			get platform list from db
			platformList = this.list(new LambdaQueryWrapper<SmsPlatform>().in(SmsPlatform::getStatus, OpenClose.OPEN));
//			save to cache
			smsCache.savePlatformList(platformList);
		}
//		return platform list
		return platformList;
	}

}