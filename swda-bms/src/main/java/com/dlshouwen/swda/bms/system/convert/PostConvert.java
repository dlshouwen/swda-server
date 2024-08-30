package com.dlshouwen.swda.bms.system.convert;

import com.dlshouwen.swda.bms.system.entity.Post;
import com.dlshouwen.swda.bms.system.vo.PostVO;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * post convert
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostConvert {
	
	/** instance */
	PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

	/**
	 * convert
	 * @param postVO
	 * @return post
	 */
	Post convert(PostVO vo);

	/**
	 * convert to vo
	 * @param post
	 * @return post vo
	 */
	PostVO convert2VO(Post entity);

	/**
	 * convert to vo list
	 * @param postList
	 * @return post vo list
	 */
	List<PostVO> convert2VOList(List<Post> list);

}
