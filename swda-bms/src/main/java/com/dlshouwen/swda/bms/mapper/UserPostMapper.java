package com.dlshouwen.swda.bms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dlshouwen.swda.bms.entity.UserPost;
import com.dlshouwen.swda.core.mapper.BaseMapper;

import java.util.List;

/**
 * user post
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Mapper
public interface UserPostMapper extends BaseMapper<UserPost> {

	/**
	 * get post id list
	 * @param userId
	 * @return post id list
	 */
	List<Long> getPostIdList(@Param("userId") Long userId);

}