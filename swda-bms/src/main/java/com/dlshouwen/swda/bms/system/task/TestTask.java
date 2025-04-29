package com.dlshouwen.swda.bms.system.task;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * test task
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
@Service
public class TestTask {
	
	/**
	 * run
	 * @param params
	 * @throws InterruptedException
	 */
	public void run(String params) throws InterruptedException {
//		log
		log.info("test task, params: {}", params);
//		sleep
		Thread.sleep(1000);
    }

}