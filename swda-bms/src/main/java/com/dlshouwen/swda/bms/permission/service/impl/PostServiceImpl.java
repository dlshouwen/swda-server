package com.dlshouwen.swda.bms.permission.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.permission.convert.PostConvert;
import com.dlshouwen.swda.bms.permission.entity.Post;
import com.dlshouwen.swda.bms.permission.mapper.PostMapper;
import com.dlshouwen.swda.bms.permission.service.IPostService;
import com.dlshouwen.swda.bms.permission.service.IUserPostService;
import com.dlshouwen.swda.bms.permission.vo.PostVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * post service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class PostServiceImpl extends BaseServiceImpl<PostMapper, Post> implements IPostService {
	
	/** user post service */
	private final IUserPostService userPostService;

	/**
	 * get post page result
	 * @param query
	 * @return post page result
	 */
	@Override
	public PageResult<PostVO> getPostPageResult(Query<Post> query) {
//		query page
		IPage<Post> page = this.page(query);
//		convert to vo for return
		return new PageResult<>(PostConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}

	/**
	 * get post list
	 * @return post vo list
	 */
	@Override
	public List<PostVO> getPostList() {
//		get post list
		List<Post> postList = this.list();
//		convert to post vo for return
		return PostConvert.INSTANCE.convert2VOList(postList);
	}
	
	/**
	 * get post data
	 * @param postId
	 * @return post data
	 */
	@Override
	public PostVO getPostData(Long postId) {
//		get post data
		Post post = this.getById(postId);
//		convert to vo for return
		return PostConvert.INSTANCE.convert2VO(post);
	}

	/**
	 * add post
	 * @param postVO
	 */
	@Override
	public void addPost(PostVO postVO) {
//		convert to post
		Post post = PostConvert.INSTANCE.convert(postVO);
//		insert post
		this.save(post);
	}

	/**
	 * update post
	 * @param postVO
	 */
	@Override
	public void updatePost(PostVO postVO) {
//		convert to post
		Post post = PostConvert.INSTANCE.convert(postVO);
//		update post
		this.updateById(post);
	}

	/**
	 * delete post
	 * @param postIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deletePost(List<Long> postIdList) {
//		delete post
		this.removeByIds(postIdList);
//		delete user post
		userPostService.deleteUserPostByPostIdList(postIdList);
	}

	/**
	 * get post name list
	 * @param postIdList
	 * @return post name list
	 */
	@Override
	public List<String> getPostNameList(List<Long> postIdList) {
//		if post id list is empty then return null
		if (postIdList.isEmpty()) {
			return null;
		}
//		get post name list for return
		return this.listByIds(postIdList).stream().map(Post::getPostName).toList();
	}

}