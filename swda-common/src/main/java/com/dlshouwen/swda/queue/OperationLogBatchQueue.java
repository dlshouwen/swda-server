package com.dlshouwen.swda.queue;

import com.dlshouwen.swda.entity.base.Data;
import com.dlshouwen.swda.entity.log.OperationLog;
import com.dlshouwen.swda.utils.LogUtils;

import cn.hutool.core.map.MapUtil;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * operation log batch queue
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class OperationLogBatchQueue<T> {

	/** template */
	@Resource
	JdbcTemplate template;

	/** buffer */
	private int buffer = -1;
	/** timeout */
	private int timeout = -1;
	/** consumer */
	private final Consumer<List<T>> consumer;

	/** looping */
	private final AtomicBoolean looping = new AtomicBoolean(false);
	/** queue */
	private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();
	/** executor service */
	private final ExecutorService service = Executors.newCachedThreadPool();

	/** start time millis */
	private AtomicLong start = new AtomicLong(System.currentTimeMillis());

	/**
	 * construct
	 */
	@SuppressWarnings("unchecked")
	public OperationLogBatchQueue() {
//		init consumer
		this.consumer = dataList -> {
//			if empty
			if(dataList==null||dataList.isEmpty()){
				return;
			}
//			insert operation log
			LogUtils.insertOperationLog(template, (List<OperationLog>)dataList);
		};
	}

	/**
	 * add to queue
	 * @param data
	 * @return result
	 */
	public boolean add(T data) {
//		init buffer / timeout
		if(buffer==-1) buffer=MapUtil.getInt(Data.attr, "operation_log_buffer_size", 100);
		if(timeout==-1) timeout=MapUtil.getInt(Data.attr, "operation_log_timeout", 100);
//		add queue
		boolean result = queue.add(data);
//		if loop then set
		if(!looping.get() && result) {
//			looping set true
			looping.set(true);
//			start loop
			startLoop();
		}
//		return
		return result;
	}

	/**
	 * complete all
	 */
	public void completeAll() {
//		while not empty
		while (!queue.isEmpty()) {
//			drain to consume
			drainToConsume();
		}
	}

	/**
	 * start loop
	 */
	private void startLoop() {
//		execute
		service.execute(new ExecuteThread());
	}

	/**
	 * drain to consume
	 */
	private void drainToConsume() {
//		defined drained
		List<T> drained = new ArrayList<>();
//		drain to datas
		int count = queue.drainTo(drained);
//		has data
		if(count>0) {
//			consume
			consumer.accept(drained);
//			set start
			start.set(System.currentTimeMillis());
		}
	}

	/**
	 * execute thread
	 */
	private class ExecuteThread implements Runnable {
		@Override
		public void run() {
//			set start
			start = new AtomicLong(System.currentTimeMillis());
//			while ture
			while(true) {
//				get last
				long last = System.currentTimeMillis() - start.get() ;
//				if size > buffer or timeout
				if (queue.size()>=buffer||(!queue.isEmpty()&&last>timeout)) {
//					drain to consume
					drainToConsume();
				} else if(queue.isEmpty()) {
//					set false
					looping.set(false);
					break;
				}
			}
		}
	}

}
