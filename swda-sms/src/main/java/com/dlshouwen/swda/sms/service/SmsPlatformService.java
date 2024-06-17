package com.dlshouwen.swda.sms.service;

import java.util.List;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.sms.entity.SmsPlatform;

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