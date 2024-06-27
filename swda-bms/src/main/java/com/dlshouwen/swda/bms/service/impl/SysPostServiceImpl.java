package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.SysPostConvert;
import com.dlshouwen.swda.bms.mapper.SysPostDao;
import com.dlshouwen.swda.bms.entity.SysPostEntity;
import com.dlshouwen.swda.bms.query.SysPostQuery;
import com.dlshouwen.swda.bms.service.SysPostService;
import com.dlshouwen.swda.bms.service.SysUserPostService;
import com.dlshouwen.swda.bms.vo.SysPostVO;
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
public class SysPostServiceImpl extends BaseServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {
	
	/** user post service */
	private final SysUserPostService sysUserPostService;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<SysPostVO> page(SysPostQuery query) {
//		select page
		IPage<SysPostEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(SysPostConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get post list
	 * @return post vo list
	 */
	@Override
	public List<SysPostVO> getList() {
//		create post query
		SysPostQuery query = new SysPostQuery();
//		set status
		query.setStatus(1);
//		get post list
		List<SysPostEntity> entityList = baseMapper.selectList(getWrapper(query));
//		convert to post vo for return
		return SysPostConvert.INSTANCE.convertList(entityList);
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
		return baseMapper.selectBatchIds(idList).stream().map(SysPostEntity::getPostName).toList();
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private Wrapper<SysPostEntity> getWrapper(SysPostQuery query) {
//		create wrapper
		LambdaQueryWrapper<SysPostEntity> wrapper = new LambdaQueryWrapper<>();
//		set condition
		wrapper.like(StrUtil.isNotBlank(query.getPostCode()), SysPostEntity::getPostCode, query.getPostCode());
		wrapper.like(StrUtil.isNotBlank(query.getPostName()), SysPostEntity::getPostName, query.getPostName());
		wrapper.eq(query.getStatus() != null, SysPostEntity::getStatus, query.getStatus());
//		set sort
		wrapper.orderByAsc(SysPostEntity::getSort);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param postVO
	 */
	@Override
	public void save(SysPostVO vo) {
//		convert to post
		SysPostEntity entity = SysPostConvert.INSTANCE.convert(vo);
//		insert post
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param postVO
	 */
	@Override
	public void update(SysPostVO vo) {
//		convert to post
		SysPostEntity entity = SysPostConvert.INSTANCE.convert(vo);
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