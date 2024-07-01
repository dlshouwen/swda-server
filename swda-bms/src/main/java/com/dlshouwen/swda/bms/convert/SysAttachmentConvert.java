package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.Attachment;
import com.dlshouwen.swda.bms.vo.AttachmentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * attachment convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysAttachmentConvert {
	
	/** instance */
	SysAttachmentConvert INSTANCE = Mappers.getMapper(SysAttachmentConvert.class);

	/**
	 * convert
	 * @param attachmentVO
	 * @return attachment
	 */
	Attachment convert(AttachmentVO vo);

	/**
	 * convert
	 * @param attachment
	 * @return attachment vo
	 */
	AttachmentVO convert(Attachment entity);

	/**
	 * convert list
	 * @param attachmentList
	 * @return attachment vo list
	 */
	List<AttachmentVO> convertList(List<Attachment> list);

}