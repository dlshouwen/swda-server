package com.dlshouwen.swda.bms.sms.service;

import java.util.List;

import com.dlshouwen.swda.bms.sms.entity.SmsPlatform;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * sms platform service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SmsPlatformService extends BaseService<SmsPlatform> {

	/**
	 * get enabled platform list
	 * @return enabled platform list
	 */
	List<SmsPlatform> getEnabledPlatformList();

}