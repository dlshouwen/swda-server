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
 * 附件管理
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachmentDao, SysAttachmentEntity>
		implements SysAttachmentService {

	@Override
	public PageResult<SysAttachmentVO> page(SysAttachmentQuery query) {
		IPage<SysAttachmentEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

		return new PageResult<>(SysAttachmentConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	private LambdaQueryWrapper<SysAttachmentEntity> getWrapper(SysAttachmentQuery query) {
		LambdaQueryWrapper<SysAttachmentEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(StrUtil.isNotBlank(query.getPlatform()), SysAttachmentEntity::getPlatform, query.getPlatform());
		wrapper.like(StrUtil.isNotBlank(query.getName()), SysAttachmentEntity::getName, query.getName());
		wrapper.orderByDesc(SysAttachmentEntity::getId);
		return wrapper;
	}

	@Override
	public void save(SysAttachmentVO vo) {
		SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);

		baseMapper.insert(entity);
	}

	@Override
	public void update(SysAttachmentVO vo) {
		SysAttachmentEntity entity = SysAttachmentConvert.INSTANCE.convert(vo);

		updateById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
		removeByIds(idList);
	}

}