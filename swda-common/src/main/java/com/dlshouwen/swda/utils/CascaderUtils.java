package com.dlshouwen.swda.utils;

import com.dlshouwen.swda.entity.base.CascaderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * cascader utils
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class CascaderUtils {

	/**
	 * generate
	 * <p>convert cascader item list to relation datas with children</p>
	 * @return 级联选择数据
	 */
	public static List<CascaderItem> generate(List<CascaderItem> cascaderItemList, String pid) {
//		defined data list
		List<CascaderItem> dataList = null;
//		for each cascader item
		for(CascaderItem cascaderItem : cascaderItemList) {
//			if now pid equals pid
			if(cascaderItem.getPid().equals(pid)) {
//				if data list is null then init
				if(dataList==null) {
					dataList = new ArrayList<>();
				}
//				add cascader item
				dataList.add(cascaderItem);
//				iterator children datas
				cascaderItem.setChildren(generate(cascaderItemList, cascaderItem.getId()));
			}
		}
//		return
		return dataList;
	}
	
}