package com.dlshouwen.swda.bms.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dlshouwen.swda.bms.permission.entity.UserPost;
import com.dlshouwen.swda.bms.permission.mapper.UserPostMapper;
import com.dlshouwen.swda.bms.permission.service.IUserPostService;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * user post service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
public class UserPostServiceImpl extends BaseServiceImpl<UserPostMapper, UserPost> implements IUserPostService {

	/**
	 * save or update
	 * @param userId
	 * @param postIdList
	 */
	@Override
	public void saveOrUpdate(Long userId, List<Long> postIdList) {
//		get post id list
		List<Long> dbPostIdList = getPostIdList(userId);
//		get insert post id list
		Collection<Long> insertPostIdList = CollUtil.subtract(postIdList, dbPostIdList);
//		if has insert datas
		if (CollUtil.isNotEmpty(insertPostIdList)) {
//			for each post id to construct user post list
			List<UserPost> postList = insertPostIdList.stream().map(postId -> {
//				create user post
				UserPost entity = new UserPost();
//				set user id, post id
				entity.setUserId(userId);
				entity.setPostId(postId);
//				return user post
				return entity;
			}).collect(Collectors.toList());
//			batch insert user post
			saveBatch(postList);
		}
//		get delete post id list
		Collection<Long> deletePostIdList = CollUtil.subtract(dbPostIdList, postIdList);
//		if has delete datas
		if (CollUtil.isNotEmpty(deletePostIdList)) {
//			get wrapper
			LambdaQueryWrapper<UserPost> queryWrapper = Wrappers.lambdaQuery();
//			delete user post
			remove(queryWrapper.eq(UserPost::getUserId, userId).in(UserPost::getPostId, deletePostIdList));
		}
	}

	/**
	 * delete user post by post id list
	 * @param postIdList
	 */
	@Override
	public void deleteUserPostByPostIdList(List<Long> postIdList) {
//		delete user post
		remove(Wrappers.<UserPost>lambdaQuery().in(UserPost::getPostId, postIdList));
	}

	/**
	 * delete user post by user id list
	 * @param userIdList
	 */
	@Override
	public void deleteUserPostByUserIdList(List<Long> userIdList) {
//		delete user post
		remove(Wrappers.<UserPost>lambdaQuery().in(UserPost::getUserId, userIdList));
	}

	/**
	 * get post id list
	 * @param userId
	 * @return post id list
	 */
	@Override
	public List<Long> getPostIdList(Long userId) {
//		get post id list for return
		return baseMapper.getPostIdList(userId);
	}

}