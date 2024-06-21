package com.dlshouwen.swda.core.task;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.cache.RedisCache;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.entity.log.DataLog;
import com.dlshouwen.swda.core.mapper.BaseMapper;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.ExceptionUtils;

import cn.hutool.core.thread.ThreadUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

/**
 * data log task
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class DataLogTask extends BaseServiceImpl<BaseMapper<DataLog>, DataLog> implements BaseService<DataLog> {

	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * run
	 */
	@PostConstruct
	public void run() {
//		get scheduled service
		ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
//		schedule run
		scheduledService.scheduleWithFixedDelay(() -> {
			try {
//				for each pop 10
				for (int i=0; i<10; i++) {
//					get data log
					DataLog dataLog = (DataLog) redisCache.rightPop(Constant.DATA_LOG_KEY);
//					if data log is null then return
					if (dataLog == null) {
						return;
					}
//					insert data log
					baseMapper.insert(dataLog);
				}
			} catch (Exception e) {
//				has error
				log.error("data log task run error: " + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}
