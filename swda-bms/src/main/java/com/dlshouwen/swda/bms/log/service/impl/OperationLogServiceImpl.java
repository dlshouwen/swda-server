package com.dlshouwen.swda.bms.log.service.impl;

import cn.hutool.core.thread.ThreadUtil;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.cache.RedisCache;
import com.dlshouwen.swda.core.base.constant.Constant;
import com.dlshouwen.swda.core.base.utils.ExceptionUtils;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * operation log service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {
	
	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * get operation log page result
	 * @param query
	 * @return operation log page result
	 */
	@Override
	public PageResult<OperationLogVO> getOperationLogPageResult(Query<OperationLog> query) {
//		query page
		IPage<OperationLog> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(OperationLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * delete operation log
	 * @param operationLogIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteOperationLog(List<Long> operationLogIdList) {
		this.removeByIds(operationLogIdList);
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
//				defined operation log list
				List<OperationLog> operationLogList = new ArrayList<>();
//				per 10
				for (int i = 0; i < 10; i++) {
//					get log
					OperationLogDTO log = (OperationLogDTO) redisCache.rightPop(Constant.OPERATION_LOG_KEY);
//					if log is null
					if (log == null) {
//						break
						break;
					}
//					convert to operation log
					OperationLog operationLog = OperationLogConvert.INSTANCE.convert(log);
//					add to operation log list
					operationLogList.add(operationLog);
				}
//				if has operation log list
				if(operationLogList.size()>0) {
//					batch insert
					baseMapper.insert(operationLogList);
				}
			} catch (Exception e) {
//				log error
				log.error("save operation log error: " + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}