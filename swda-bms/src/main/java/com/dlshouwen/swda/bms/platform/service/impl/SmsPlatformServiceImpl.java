package com.dlshouwen.swda.bms.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.core.sms.cache.SmsCache;
import com.dlshouwen.swda.bms.platform.convert.SmsPlatformConvert;
import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.bms.platform.mapper.SmsPlatformMapper;
import com.dlshouwen.swda.bms.platform.service.ISmsPlatformService;
import com.dlshouwen.swda.bms.platform.vo.SmsPlatformVO;
import com.dlshouwen.swda.core.base.dict.OpenClose;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * get sms platform page result
	 * @param query
	 * @return sms platform page result
	 */
	@Override
	public PageResult<SmsPlatformVO> getSmsPlatformPageResult(Query<SmsPlatform> query) {
//		query page
		IPage<SmsPlatform> page = this.page(query);
//		convert to vo list for page return
		return new PageResult<>(SmsPlatformConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * get sms platform list
	 * @param smsPlatformType
	 * @return sms platform list
	 */
	@Override
	public List<SmsPlatformVO> getSmsPlatformList(Integer smsPlatformType) {
//		defined wrapper
		LambdaQueryWrapper<SmsPlatform> wrapper = Wrappers.lambdaQuery();
//		eq: sms platform type
		wrapper.eq(smsPlatformType!=null, SmsPlatform::getSmsPlatformType, smsPlatformType);
//		get sms platform list
		List<SmsPlatform> smsPlatformList = baseMapper.selectList(wrapper);
//		convert to vo list for return
		return SmsPlatformConvert.INSTANCE.convert2VOList(smsPlatformList);
	}

	/**
	 * get enable sms platform list
	 * @return enable sms platform list
	 */
	@Override
	public List<SmsPlatform> getEnableSmsPlatformList() {
//		get sms platform list from cache
		List<SmsPlatform> smsPlatformList = smsCache.getSmsPlatformlist();
//		if no sms platform exists
		if (smsPlatformList == null) {
//			get enable sms platform list
			smsPlatformList = this.list(new LambdaQueryWrapper<SmsPlatform>().in(SmsPlatform::getStatus, OpenClose.OPEN));
//			save cache
			smsCache.saveSmsPlatform(smsPlatformList);
		}
//		return sms platform list
		return smsPlatformList;
	}

	/**
	 * add sms platform
	 * @param smsPlatformVO
	 */
	@Override
	public void addSmsPlatform(SmsPlatformVO smsPlatformVO) {
//		convert sms platform vo to entity
		SmsPlatform smsPlatform = SmsPlatformConvert.INSTANCE.convert(smsPlatformVO);
//		insert sms platform
		this.save(smsPlatform);
	}

	/**
	 * update sms platform
	 * @param smsPlatformVO
	 */
	@Override
	public void updateSmsPlatform(SmsPlatformVO smsPlatformVO) {
//		convert sms platform vo to entity
		SmsPlatform smsPlatform = SmsPlatformConvert.INSTANCE.convert(smsPlatformVO);
//		update sms platform
		this.updateById(smsPlatform);
	}

	/**
	 * delete sms platform
	 * @param smsPlatformIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteSmsPlatform(List<Long> smsPlatformIdList) {
//		remove by ids
		this.removeByIds(smsPlatformIdList);
	}

}