package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.AttachmentConvert;
import com.dlshouwen.swda.bms.mapper.SysAttachmentDao;
import com.dlshouwen.swda.bms.entity.Attachment;
import com.dlshouwen.swda.bms.query.SysAttachmentQuery;
import com.dlshouwen.swda.bms.service.SysAttachmentService;
import com.dlshouwen.swda.bms.vo.AttachmentVO;
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
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachmentDao, Attachment> implements SysAttachmentService {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<AttachmentVO> page(SysAttachmentQuery query) {
//		select page
		IPage<Attachment> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(AttachmentConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private LambdaQueryWrapper<Attachment> getWrapper(SysAttachmentQuery query) {
//		create wrapper
		LambdaQueryWrapper<Attachment> wrapper = Wrappers.lambdaQuery();
//		set condition
		wrapper.eq(StrUtil.isNotBlank(query.getPlatform()), Attachment::getPlatform, query.getPlatform());
		wrapper.like(StrUtil.isNotBlank(query.getName()), Attachment::getName, query.getName());
		wrapper.orderByDesc(Attachment::getId);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param attachmentVO
	 */
	@Override
	public void save(AttachmentVO vo) {
//		convert to attachment
		Attachment entity = AttachmentConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param attachmentVO
	 */
	@Override
	public void update(AttachmentVO vo) {
//		convert to attachment
		Attachment entity = AttachmentConvert.INSTANCE.convert(vo);
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