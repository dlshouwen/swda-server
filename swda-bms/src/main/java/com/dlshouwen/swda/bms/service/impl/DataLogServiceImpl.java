package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.utils.ExceptionUtils;
import com.dlshouwen.swda.core.utils.GridUtils;
import com.dlshouwen.swda.core.cache.RedisCache;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.entity.log.DataLog;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.DataLogConvert;
import com.dlshouwen.swda.bms.mapper.DataLogMapper;
import com.dlshouwen.swda.bms.service.IDataLogService;
import com.dlshouwen.swda.bms.vo.DataLogVO;
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
		IPage<DataLog> page = GridUtils.query(baseMapper, query);
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
					DataLog log = (DataLog) redisCache.rightPop(Constant.OPERATION_LOG_KEY);
//					if log is null
					if (log == null) {
						return;
					}
//					convert to data log
					DataLog dataLog = BeanUtil.copyProperties(log, DataLog.class);
//					insert
					baseMapper.insert(dataLog);
				}
			} catch (Exception e) {
//				log error
				log.error("SysLogOperateServiceImpl.saveLog Error：" + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}