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
import com.dlshouwen.swda.core.log.dto.DataLogDTO;
import com.dlshouwen.swda.core.log.entity.DataLog;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.log.convert.DataLogConvert;
import com.dlshouwen.swda.bms.log.mapper.DataLogMapper;
import com.dlshouwen.swda.bms.log.service.IDataLogService;
import com.dlshouwen.swda.bms.log.vo.DataLogVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * data log service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class DataLogServiceImpl extends BaseServiceImpl<DataLogMapper, DataLog> implements IDataLogService {
	
	/** redis cache */
	private final RedisCache redisCache;

	/**
	 * get data log page result
	 * @param query
	 * @return data log page result
	 */
	@Override
	public PageResult<DataLogVO> getDataLogPageResult(Query<DataLog> query) {
//		query page
		IPage<DataLog> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(DataLogConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * delete data log
	 * @param dataLogIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDataLog(List<Long> dataLogIdList) {
		this.removeByIds(dataLogIdList);
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
//				defined data log list
				List<DataLog> dataLogList = new ArrayList<>();
//				per 10
				for (int i = 0; i < 10; i++) {
//					get log
					DataLogDTO log = (DataLogDTO) redisCache.rightPop(Constant.DATA_LOG_KEY);
//					if log is null
					if (log == null) {
//						continue
						continue;
					}
//					convert to data log
					DataLog dataLog = DataLogConvert.INSTANCE.convert(log);
//					add to data log list
					dataLogList.add(dataLog);
				}
//				has data log list
				if(dataLogList.size()>0) {
//					batch insert
					baseMapper.insert(dataLogList);
				}
			} catch (Exception e) {
//				log error
				log.error("save data log error: " + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}