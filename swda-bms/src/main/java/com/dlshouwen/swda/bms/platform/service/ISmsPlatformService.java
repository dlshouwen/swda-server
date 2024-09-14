package com.dlshouwen.swda.bms.platform.service;

import java.util.List;

import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * sms platform service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface ISmsPlatformService extends BaseService<SmsPlatform> {

	/**
	 * get enabled platform list
	 * @return enabled platform list
	 */
	List<SmsPlatform> getEnabledPlatformList();

}