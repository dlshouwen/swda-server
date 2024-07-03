package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Attr;
import com.dlshouwen.swda.bms.vo.AttrVO;

import java.util.List;

/**
 * attr service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IAttrService extends BaseService<Attr> {

	/**
	 * get attr list
	 * @return attr list
	 */
	List<AttrVO> getAttrList();

	/**
	 * save attr list
	 * @param attrList
	 */
	void saveAttrList(List<AttrVO> attrList);
	
	/**
	 * get value
	 * @param attrId
	 * @return value
	 */
	String getValue(String attrId);

}