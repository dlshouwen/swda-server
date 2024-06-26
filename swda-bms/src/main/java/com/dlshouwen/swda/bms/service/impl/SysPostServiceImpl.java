package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.PostConvert;
import com.dlshouwen.swda.bms.mapper.PostMapper;
import com.dlshouwen.swda.bms.entity.Post;
import com.dlshouwen.swda.bms.query.SysPostQuery;
import com.dlshouwen.swda.bms.service.SysPostService;
import com.dlshouwen.swda.bms.service.SysUserPostService;
import com.dlshouwen.swda.bms.vo.PostVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * post service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysPostServiceImpl extends BaseServiceImpl<PostMapper, Post> implements SysPostService {
	
	/** user post service */
	private final SysUserPostService sysUserPostService;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<PostVO> page(SysPostQuery query) {
//		select page
		IPage<Post> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(PostConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get post list
	 * @return post vo list
	 */
	@Override
	public List<PostVO> getList() {
//		create post query
		SysPostQuery query = new SysPostQuery();
//		set status
		query.setStatus(1);
//		get post list
		List<Post> entityList = baseMapper.selectList(getWrapper(query));
//		convert to post vo for return
		return PostConvert.INSTANCE.convertList(entityList);
	}

	/**
	 * get name list
	 * @param idList
	 * @return name list
	 */
	@Override
	public List<String> getNameList(List<Long> idList) {
//		if post id list is empty then return null
		if (idList.isEmpty()) {
			return null;
		}
//		get post name list for return
		return baseMapper.selectBatchIds(idList).stream().map(Post::getPostName).toList();
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private Wrapper<Post> getWrapper(SysPostQuery query) {
//		create wrapper
		LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
//		set condition
		wrapper.like(StrUtil.isNotBlank(query.getPostCode()), Post::getPostCode, query.getPostCode());
		wrapper.like(StrUtil.isNotBlank(query.getPostName()), Post::getPostName, query.getPostName());
		wrapper.eq(query.getStatus() != null, Post::getStatus, query.getStatus());
//		set sort
		wrapper.orderByAsc(Post::getSort);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param postVO
	 */
	@Override
	public void save(PostVO vo) {
//		convert to post
		Post entity = PostConvert.INSTANCE.convert(vo);
//		insert post
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param postVO
	 */
	@Override
	public void update(PostVO vo) {
//		convert to post
		Post entity = PostConvert.INSTANCE.convert(vo);
//		update post
		updateById(entity);
	}

	/**
	 * delete
	 * @param idList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
//		delete post
		removeByIds(idList);
//		delete user post
		sysUserPostService.deleteByPostIdList(idList);
	}

}