package com.dlshouwen.swda.core.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dlshouwen.swda.core.excel.callback.ExcelFinishCallBack;

import java.util.LinkedList;
import java.util.List;

/**
 * excel data listener
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public class ExcelDataListener<T> extends AnalysisEventListener<T> {

	/** datas */
	private final List<T> datas = new LinkedList<>();

	/** call back */
	private final ExcelFinishCallBack<T> callBack;

	/**
	 * constructor
	 * @param callBack
	 */
	public ExcelDataListener(ExcelFinishCallBack<T> callBack) {
		this.callBack = callBack;
	}

	/**
	 * invoke
	 * 
	 * @param data
	 * @param context
	 */
	@Override
	public void invoke(T data, AnalysisContext context) {
//    	add data
		datas.add(data);
//    	if size over
		if (datas.size() >= 500) {
//        	do save batch
			this.callBack.doSaveBatch(datas);
//			clear
			datas.clear();
		}
	}

	/**
	 * do after all analysed
	 * @param context
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		this.callBack.doAfterAllAnalysed(this.datas);
	}

}
