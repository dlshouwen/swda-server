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
import com.dlshouwen.swda.core.log.dto.OperationLogDTO;
import com.dlshouwen.swda.core.log.entity.OperationLog;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.log.convert.OperationLogConvert;
import com.dlshouwen.swda.bms.log.mapper.OperationLogMapper;
import com.dlshouwen.swda.bms.log.service.IOperationLogService;
import com.dlshouwen.swda.bms.log.vo.OperationLogVO;

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
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {
	
	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * get operation log list
	 * @param query
	 * @return operation log list
	 */
	@Override
	public PageResult<OperationLogVO> getOperationLogList(Query<OperationLog> query) {
//		query page
		IPage<OperationLog> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(OperationLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * save log
	 */
	@PostConstruct
	public void saveOperationLog() {
//		create schedule executor
		ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
//		delay
		scheduledService.scheduleWithFixedDelay(() -> {
//			try catch
			try {
//				per 10
				for (int i = 0; i < 10; i++) {
//					get log
					OperationLogDTO log = (OperationLogDTO) redisCache.rightPop(Constant.OPERATION_LOG_KEY);
//					if log is null
					if (log == null) {
						return;
					}
//					convert to operation log
					OperationLog operationLog = OperationLogConvert.INSTANCE.convert(log);
//					insert
					baseMapper.insert(operationLog);
				}
			} catch (Exception e) {
//				log error
				log.error("SysLogOperateServiceImpl.saveLog Error：" + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}