package com.dlshouwen.swda.bms.oss.convert;

import com.dlshouwen.swda.bms.oss.entity.Attachment;
import com.dlshouwen.swda.bms.oss.vo.AttachmentVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * attachment convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachmentConvert {
	
	/** instance */
	AttachmentConvert INSTANCE = Mappers.getMapper(AttachmentConvert.class);

	/**
	 * convert
	 * @param attachmentVO
	 * @return attachment
	 */
	Attachment convert(AttachmentVO vo);

	/**
	 * convert to vo
	 * @param attachment
	 * @return attachment vo
	 */
	AttachmentVO convert2VO(Attachment entity);

	/**
	 * convert to vo list
	 * @param attachmentList
	 * @return attachment vo list
	 */
	List<AttachmentVO> convert2VOList(List<Attachment> list);

}