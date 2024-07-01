package com.dlshouwen.swda.bms.convert;

import com.dlshouwen.swda.bms.entity.SysPostEntity;
import com.dlshouwen.swda.bms.vo.PostVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * post convert
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface SysPostConvert {
	
	/** instance */
	SysPostConvert INSTANCE = Mappers.getMapper(SysPostConvert.class);

	/**
	 * convert
	 * @param post
	 * @return post vo
	 */
	PostVO convert(SysPostEntity entity);

	/**
	 * convert
	 * @param postVO
	 * @return post
	 */
	SysPostEntity convert(PostVO vo);

	/**
	 * convert list
	 * @param postList
	 * @return post vo list
	 */
	List<PostVO> convertList(List<SysPostEntity> list);

}
