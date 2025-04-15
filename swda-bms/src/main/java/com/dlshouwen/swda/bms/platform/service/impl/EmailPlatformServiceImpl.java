package com.dlshouwen.swda.bms.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.core.email.cache.EmailCache;
import com.dlshouwen.swda.bms.platform.convert.EmailPlatformConvert;
import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.bms.platform.mapper.EmailPlatformMapper;
import com.dlshouwen.swda.bms.platform.service.IEmailPlatformService;
import com.dlshouwen.swda.bms.platform.vo.EmailPlatformVO;
import com.dlshouwen.swda.core.base.dict.OpenClose;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * email platform service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class EmailPlatformServiceImpl extends BaseServiceImpl<EmailPlatformMapper, EmailPlatform>
		implements IEmailPlatformService {
	
	/** email cache */
	private final EmailCache emailCache;

	/**
	 * get email platform page result
	 * @param query
	 * @return email platform page result
	 */
	@Override
	public PageResult<EmailPlatformVO> getEmailPlatformPageResult(Query<EmailPlatform> query) {
//		query page
		IPage<EmailPlatform> page = this.page(query);
//		convert to vo list for page return
		return new PageResult<>(EmailPlatformConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * get email platform list
	 * @param emailPlatformType
	 * @return email platform list
	 */
	@Override
	public List<EmailPlatformVO> getEmailPlatformList(Integer emailPlatformType) {
//		defined wrapper
		LambdaQueryWrapper<EmailPlatform> wrapper = Wrappers.lambdaQuery();
//		eq: email platform type
		wrapper.eq(emailPlatformType!=null, EmailPlatform::getEmailPlatformType, emailPlatformType);
//		get email platform list
		List<EmailPlatform> emailPlatformList = baseMapper.selectList(wrapper);
//		convert to vo list for return
		return EmailPlatformConvert.INSTANCE.convert2VOList(emailPlatformList);
	}

	/**
	 * get enable email platform list
	 * @return enable email platform list
	 */
	@Override
	public List<EmailPlatform> getEnableEmailPlatformList() {
//		get email platform list from cache
		List<EmailPlatform> emailPlatformList = emailCache.getEmailPlatformList();
//		if no email platform exists
		if (emailPlatformList == null) {
//			get enable email platform list
			emailPlatformList = this.list(new LambdaQueryWrapper<EmailPlatform>().in(EmailPlatform::getStatus, OpenClose.OPEN));
//			save cache
			emailCache.saveEmailPlatform(emailPlatformList);
		}
//		return email platform list
		return emailPlatformList;
	}

	/**
	 * add email platform
	 * @param emailPlatformVO
	 */
	@Override
	public void addEmailPlatform(EmailPlatformVO emailPlatformVO) {
//		convert email platform vo to entity
		EmailPlatform emailPlatform = EmailPlatformConvert.INSTANCE.convert(emailPlatformVO);
//		insert email platform
		this.save(emailPlatform);
	}

	/**
	 * update email platform
	 * @param emailPlatformVO
	 */
	@Override
	public void updateEmailPlatform(EmailPlatformVO emailPlatformVO) {
//		convert email platform vo to entity
		EmailPlatform emailPlatform = EmailPlatformConvert.INSTANCE.convert(emailPlatformVO);
//		update email platform
		this.updateById(emailPlatform);
	}

	/**
	 * delete email platform
	 * @param emailPlatformIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteEmailPlatform(List<Long> emailPlatformIdList) {
//		remove by ids
		this.removeByIds(emailPlatformIdList);
	}

}