package com.dlshouwen.swda.bms.platform.service;

import java.util.List;

import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.bms.platform.vo.EmailPlatformVO;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;

/**
 * email platform service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IEmailPlatformService extends BaseService<EmailPlatform> {

	/**
	 * get email platform page result
	 * @param query
	 * @return email platform page result
	 */
	PageResult<EmailPlatformVO> getEmailPlatformPageResult(Query<EmailPlatform> query);

	/**
	 * get email platform list
	 * @param emailPlatformType
	 * @return email platform list
	 */
	List<EmailPlatformVO> getEmailPlatformList(String emailPlatformType);

	/**
	 * get enable email platform list
	 * @return enable email platform list
	 */
	List<EmailPlatform> getEnableEmailPlatformList();

	/**
	 * add email platform
	 * @param emailPlatformVO
	 */
	void addEmailPlatform(EmailPlatformVO emailPlatformVO);

	/**
	 * update email platform
	 * @param emailPlatformVO
	 */
	void updateEmailPlatform(EmailPlatformVO emailPlatformVO);

	/**
	 * delete email platform
	 * @param emailPlatformIdList
	 */
	void deleteEmailPlatform(List<Long> emailPlatformIdList);

}