package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.utils.ExceptionUtils;
import com.dlshouwen.swda.core.cache.RedisCache;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.entity.log.OperationLog;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.SysLogOperateConvert;
import com.dlshouwen.swda.bms.mapper.SysLogOperateDao;
import com.dlshouwen.swda.bms.entity.SysLogOperateEntity;
import com.dlshouwen.swda.bms.query.SysLogOperateQuery;
import com.dlshouwen.swda.bms.service.SysLogOperateService;
import com.dlshouwen.swda.bms.vo.SysLogOperateVO;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * operation log service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysLogOperateServiceImpl extends BaseServiceImpl<SysLogOperateDao, SysLogOperateEntity> implements SysLogOperateService {
	
	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<SysLogOperateVO> page(SysLogOperateQuery query) {
//		select page
		IPage<SysLogOperateEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(SysLogOperateConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private LambdaQueryWrapper<SysLogOperateEntity> getWrapper(SysLogOperateQuery query) {
//		defined wrapper
		LambdaQueryWrapper<SysLogOperateEntity> wrapper = Wrappers.lambdaQuery();
//		set condition
		wrapper.eq(query.getStatus() != null, SysLogOperateEntity::getStatus, query.getStatus());
		wrapper.like(StrUtil.isNotBlank(query.getRealName()), SysLogOperateEntity::getRealName, query.getRealName());
		wrapper.like(StrUtil.isNotBlank(query.getModule()), SysLogOperateEntity::getModule, query.getModule());
		wrapper.like(StrUtil.isNotBlank(query.getReqUri()), SysLogOperateEntity::getReqUri, query.getReqUri());
		wrapper.orderByDesc(SysLogOperateEntity::getId);
//		return wrapper
		return wrapper;
	}

	/**
	 * save log
	 */
	@PostConstruct
	public void saveLog() {
//		create schedule executor
		ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
//		delay
		scheduledService.scheduleWithFixedDelay(() -> {
//			try catch
			try {
//				per 10
				for (int i = 0; i < 10; i++) {
//					get log
					OperationLog log = (OperationLog) redisCache.rightPop(Constant.OPERATION_LOG_KEY);
//					if log is null
					if (log == null) {
						return;
					}
//					convert to operation log
					SysLogOperateEntity entity = BeanUtil.copyProperties(log, SysLogOperateEntity.class);
//					insert
					baseMapper.insert(entity);
				}
			} catch (Exception e) {
//				log error
				log.error("SysLogOperateServiceImpl.saveLog Error：" + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}