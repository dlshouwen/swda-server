package com.dlshouwen.swda.bms.log.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.cache.RedisCache;
import com.dlshouwen.swda.core.common.constant.Constant;
import com.dlshouwen.swda.core.common.utils.ExceptionUtils;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.dto.DataLogDTO;
import com.dlshouwen.swda.core.log.entity.DataLog;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.log.convert.DataLogConvert;
import com.dlshouwen.swda.bms.log.mapper.DataLogMapper;
import com.dlshouwen.swda.bms.log.service.IDataLogService;
import com.dlshouwen.swda.bms.log.vo.DataLogVO;

import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * data log service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class DataLogServiceImpl extends BaseServiceImpl<DataLogMapper, DataLog> implements IDataLogService {
	
	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * get data log list
	 * @param query
	 * @return data log list
	 */
	@Override
	public PageResult<DataLogVO> getDataLogList(Query<DataLog> query) {
//		query page
		IPage<DataLog> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(DataLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * save log
	 */
	@PostConstruct
	public void saveDataLog() {
//		create schedule executor
		ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
//		delay
		scheduledService.scheduleWithFixedDelay(() -> {
//			try catch
			try {
//				per 10
				for (int i = 0; i < 10; i++) {
//					get log
					DataLogDTO log = (DataLogDTO) redisCache.rightPop(Constant.DATA_LOG_KEY);
//					if log is null
					if (log == null) {
						return;
					}
//					convert to data log
					DataLog dataLog = DataLogConvert.INSTANCE.convert(log);
//					insert
					baseMapper.insert(dataLog);
				}
			} catch (Exception e) {
//				log error
				log.error("DataLogServiceImpl.saveLog Error：" + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}