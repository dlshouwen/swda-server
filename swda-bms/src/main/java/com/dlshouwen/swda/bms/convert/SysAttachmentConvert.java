package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysAttachmentEntity;
import com.dlshouwen.swda.bms.vo.SysAttachmentVO;
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
	SysAttachmentEntity convert(SysAttachmentVO vo);

	/**
	 * convert
	 * @param attachment
	 * @return attachment vo
	 */
	SysAttachmentVO convert(SysAttachmentEntity entity);

	/**
	 * convert list
	 * @param attachmentList
	 * @return attachment vo list
	 */
	List<SysAttachmentVO> convertList(List<SysAttachmentEntity> list);

}