package com.dlshouwen.swda.core.task;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.core.entity.log.DataLog;
import com.dlshouwen.swda.core.loader.DataLoader;
import com.dlshouwen.swda.core.mapper.BaseMapper;
import com.dlshouwen.swda.core.property.UniqueProperties;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.ExceptionUtils;

import cn.hutool.core.thread.ThreadUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

/**
 * data load task
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class DataLoadTask extends BaseServiceImpl<BaseMapper<DataLog>, DataLog> implements BaseService<DataLog> {

	/** unique properties */
	private final UniqueProperties uniqueProperties;
	
	/** jdbc template */
	private final JdbcTemplate jdbcTemplate;

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
//				laod data
				DataLoader.load(uniqueProperties, jdbcTemplate);
			} catch (Exception e) {
//				has error
				log.error("data load task run error: " + ExceptionUtils.toString(e));
			}
		}, 1, 10, TimeUnit.SECONDS);
	}

}
