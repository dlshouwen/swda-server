package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysAttachmentEntity;
import com.dlshouwen.swda.bms.vo.SysAttachmentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 附件管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysAttachmentConvert {
	SysAttachmentConvert INSTANCE = Mappers.getMapper(SysAttachmentConvert.class);

	SysAttachmentEntity convert(SysAttachmentVO vo);

	SysAttachmentVO convert(SysAttachmentEntity entity);

	List<SysAttachmentVO> convertList(List<SysAttachmentEntity> list);

}