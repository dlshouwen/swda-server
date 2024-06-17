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

@Service
@AllArgsConstructor
public class DataLoadTask extends BaseServiceImpl<BaseMapper<DataLog>, DataLog> implements BaseService<DataLog> {
	
	private final UniqueProperties uniqueProperties;
	private final JdbcTemplate jdbcTemplate;
	
    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);

        // 每隔10秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
            	DataLoader.load(uniqueProperties, jdbcTemplate);
            } catch (Exception e) {
                log.error("LogTask.saveLog Error：" + ExceptionUtils.toString(e));
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

}
