package com.dlshouwen.swda.bms.service;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.service.BaseService;
import com.dlshouwen.swda.bms.entity.Post;
import com.dlshouwen.swda.bms.vo.PostVO;

import java.util.List;

/**
 * post service
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public interface IPostService extends BaseService<Post> {

	/**
	 * get post list
	 * @param query
	 * @return page result
	 */
	PageResult<PostVO> getPostList(Query<Post> query);

	/**
	 * get post list
	 * @return post list
	 */
	List<PostVO> getPostList();
	
	/**
	 * get post data
	 * @param postId
	 * @return post data
	 */
	PostVO getPostData(Long postId);

	/**
	 * add post
	 * @param postVO
	 */
	void addPost(PostVO vo);

	/**
	 * update post
	 * @param postVO
	 */
	void updatePost(PostVO vo);

	/**
	 * delete post
	 * @param postIdList
	 */
	void deletePost(List<Long> postIdList);

	/**
	 * get post name list
	 * @param postIdList
	 * @return post name list
	 */
	List<String> getPostNameList(List<Long> postIdList);

}