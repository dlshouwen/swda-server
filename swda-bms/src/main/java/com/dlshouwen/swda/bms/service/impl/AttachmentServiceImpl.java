package com.dlshouwen.swda.bms.service.impl;

import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.GridUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dlshouwen.swda.bms.convert.AttachmentConvert;
import com.dlshouwen.swda.bms.mapper.AttachmentMapper;
import com.dlshouwen.swda.bms.entity.Attachment;
import com.dlshouwen.swda.bms.service.IAttachmentService;
import com.dlshouwen.swda.bms.vo.AttachmentVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * attachment service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class AttachmentServiceImpl extends BaseServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {

	/**
	 * get attachment list
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<AttachmentVO> getAttachmentList(Query<Attachment> query) {
//		query page
		IPage<Attachment> page = GridUtils.query(baseMapper, query);
//		convert to vo list for page return
		return new PageResult<>(AttachmentConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get attachment data
	 * @param attachmentId
	 * @return attachment
	 */
	@Override
	public AttachmentVO getAttachmentData(Long attachmentId) {
//		get attachment
		Attachment attachment = this.getById(attachmentId);
//		convert to vo for return
		return AttachmentConvert.INSTANCE.convert2VO(attachment);
	}

	/**
	 * add attachment
	 * @param attachmentVO
	 */
	@Override
	public void addAttachment(AttachmentVO attachmentVO) {
//		convert to attachment
		Attachment attachment = AttachmentConvert.INSTANCE.convert(attachmentVO);
//		insert attachment
		this.save(attachment);
	}

	/**
	 * update attachment
	 * @param attachmentVO
	 */
	@Override
	public void updateAttachment(AttachmentVO attachmentVO) {
//		convert to attachment
		Attachment attachment = AttachmentConvert.INSTANCE.convert(attachmentVO);
//		update attachment
		this.updateById(attachment);
	}

	/**
	 * delete attachment
	 * @param attachmentIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAttachment(List<Long> attachmentIdList) {
//		delete attachment
		this.removeByIds(attachmentIdList);
	}

}