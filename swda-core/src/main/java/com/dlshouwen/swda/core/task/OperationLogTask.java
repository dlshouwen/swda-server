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

@Service
@AllArgsConstructor
public class OperationLogTask extends BaseServiceImpl<BaseMapper<OperationLog>, OperationLog> implements BaseService<OperationLog> {
	
	private final RedisCache redisCache;
	
    /**
     * 启动项目时，从Redis队列获取操作日志并保存
     */
    @PostConstruct
    public void saveLog() {
        ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);

        // 每隔10秒钟，执行一次
        scheduledService.scheduleWithFixedDelay(() -> {
            try {
                // 每次插入10条
                int count = 10;
                for (int i = 0; i < count; i++) {
                    OperationLog log = (OperationLog) redisCache.rightPop(Constant.OPERATION_LOG_KEY);
                    if (log == null) {
                        return;
                    }
                    baseMapper.insert(log);
                }
            } catch (Exception e) {
                log.error("LogTask.saveLog Error：" + ExceptionUtils.toString(e));
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

}
