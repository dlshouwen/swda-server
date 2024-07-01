package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.SysAttachmentEntity;
import com.dlshouwen.swda.bms.query.SysAttachmentQuery;
import com.dlshouwen.swda.bms.vo.AttachmentVO;

import java.util.List;

/**
 * attachment service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface SysAttachmentService extends BaseService<SysAttachmentEntity> {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	PageResult<AttachmentVO> page(SysAttachmentQuery query);

	/**
	 * save
	 * @param attachmentVO
	 */
	void save(AttachmentVO vo);

	/**
	 * update
	 * @param attachmentVO
	 */
	void update(AttachmentVO vo);

	/**
	 * delete
	 * @param idList
	 */
	void delete(List<Long> idList);

}