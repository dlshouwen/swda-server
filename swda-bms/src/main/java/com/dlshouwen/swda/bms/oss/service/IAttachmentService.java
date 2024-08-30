package com.dlshouwen.swda.bms.oss.service;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.BaseService;
import com.dlshouwen.swda.bms.oss.entity.Attachment;
import com.dlshouwen.swda.bms.oss.vo.AttachmentVO;

import java.util.List;

/**
 * attachment service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
public interface IAttachmentService extends BaseService<Attachment> {

	/**
	 * get attachment list
	 * @param query
	 * @return page result
	 */
	PageResult<AttachmentVO> getAttachmentList(Query<Attachment> query);
	
	/**
	 * get attachment data
	 * @param attachmentId
	 * @return attachment
	 */
	AttachmentVO getAttachmentData(Long attachmentId);

	/**
	 * add attachment
	 * @param attachmentVO
	 */
	void addAttachment(AttachmentVO vo);

	/**
	 * update attachment
	 * @param attachmentVO
	 */
	void updateAttachment(AttachmentVO vo);

	/**
	 * delete attachment
	 * @param attachmentIdList
	 */
	void deleteAttachment(List<Long> attachmentIdList);

}