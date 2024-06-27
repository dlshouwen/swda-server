package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.SysAttachmentConvert;
import com.dlshouwen.swda.bms.mapper.SysAttachmentDao;
import com.dlshouwen.swda.bms.entity.SysAttachmentEntity;
import com.dlshouwen.swda.bms.query.SysAttachmentQuery;
import com.dlshouwen.swda.bms.service.SysAttachmentService;
import com.dlshouwen.swda.bms.vo.SysAttachmentVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * attachment service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachmentDao, SysAttachmentEntity> implements SysAttachmentService {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<SysAttachmentVO> page(SysAttachmentQuery query) {
//		select page
		IPage<SysAttachmentEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(SysAttachmentConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private LambdaQueryWrapper<SysAttachmentEntity> getWrapper(SysAttachmentQuery query) {
//		create wrapper
		LambdaQueryWrapper<SysAttachmentEntity> wrapper = Wrappers.lambdaQuery();
//		set condition
		wrapper.eq(StrUtil.isNotBlank(query.getPlatform()), SysAttachmentEntity::getPlatform, query.getPlatform());
		wrapper.like(StrUtil.isNotBlank(query.getName()), SysAttachmentEntity::getName, query.getName());
		wrapper.orderByDesc(SysAttachmentEntity::getId);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param attachmentVO
	 */
	@Override
	public void save(SysAttachmentVO vo) {
//		convert to attachment
		SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param attachmentVO
	 */
	@Override
	public void update(SysAttachmentVO vo) {
//		convert to attachment
		SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);
//		update
		updateById(entity);
	}

	/**
	 * delete
	 * @param idList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
//		delete
		removeByIds(idList);
	}

}