package com.dlshouwen.swda.core.excel.callback;

import java.util.List;

/**
 * excel finish callback
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface ExcelFinishCallBack<T> {

	/**
	 * do after all analysed
	 * @param result
	 */
	void doAfterAllAnalysed(List<T> result);

	/**
	 * do save batch
	 * @param result
	 */
	default void doSaveBatch(List<T> result) {
	}

}
