package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysPostEntity;
import com.dlshouwen.swda.bms.query.SysPostQuery;
import com.dlshouwen.swda.bms.vo.SysPostVO;

import java.util.List;

/**
 * post service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysPostService extends BaseService<SysPostEntity> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<SysPostVO> page(SysPostQuery query);

	/**
	 * get post list
	 * @return post list
	 */
	List<SysPostVO> getList();

	/**
	 * get name list
	 * @param idList
	 * @return name list
	 */
	List<String> getNameList(List<Long> idList);

	/**
	 * save
	 * @param postVO
	 */
	void save(SysPostVO vo);

	/**
	 * update
	 * @param postVO
	 */
	void update(SysPostVO vo);

	/**
	 * delete
	 * @param idList
	 */
	void delete(List<Long> idList);

}