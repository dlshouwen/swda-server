package com.dlshouwen.swda.bms.platform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.bms.core.sms.cache.SmsCache;
import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.bms.platform.mapper.SmsPlatformMapper;
import com.dlshouwen.swda.bms.platform.service.ISmsPlatformService;
import com.dlshouwen.swda.core.common.dict.OpenClose;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sms platform service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class SmsPlatformServiceImpl extends BaseServiceImpl<SmsPlatformMapper, SmsPlatform> implements ISmsPlatformService {

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
			platformList = this.list(Wrappers.<SmsPlatform>lambdaQuery().in(SmsPlatform::getStatus, OpenClose.OPEN));
//			save to cache
			smsCache.savePlatformList(platformList);
		}
//		return platform list
		return platformList;
	}

}