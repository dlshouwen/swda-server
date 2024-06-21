package com.dlshouwen.swda.core.task;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.cache.RedisCache;
import com.dlshouwen.swda.core.constant.Constant;
import com.dlshouwen.swda.core.entity.log.OperationLog;
import com.dlshouwen.swda.core.mapper.BaseMapper;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.ExceptionUtils;

import cn.hutool.core.thread.ThreadUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

/**
 * operation log task
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class OperationLogTask extends BaseServiceImpl<BaseMapper<OperationLog>, OperationLog> implements BaseService<OperationLog> {

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
					OperationLog operationLog = (OperationLog) redisCache.rightPop(Constant.OPERATION_LOG_KEY);
//					if operation log is null then return
					if (operationLog == null) {
						return;
					}
//					insert operation log
					baseMapper.insert(operationLog);
				}
			} catch (Exception e) {
//				has error
				log.error("operation log task run error: " + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}
