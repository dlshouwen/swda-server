package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.SysDictDataConvert;
import com.dlshouwen.swda.bms.mapper.SysDictDataDao;
import com.dlshouwen.swda.bms.entity.SysDictDataEntity;
import com.dlshouwen.swda.bms.query.SysDictDataQuery;
import com.dlshouwen.swda.bms.service.SysDictDataService;
import com.dlshouwen.swda.bms.vo.SysDictDataVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * dict service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<SysDictDataVO> page(SysDictDataQuery query) {
//		select page
		IPage<SysDictDataEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(SysDictDataConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private Wrapper<SysDictDataEntity> getWrapper(SysDictDataQuery query) {
//		create wrapper
		LambdaQueryWrapper<SysDictDataEntity> wrapper = new LambdaQueryWrapper<>();
//		set condition
		wrapper.eq(SysDictDataEntity::getDictTypeId, query.getDictTypeId());
		wrapper.orderByAsc(SysDictDataEntity::getSort);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param dictVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysDictDataVO vo) {
//		get dict
		SysDictDataEntity sysDictData = getOne(
				Wrappers.<SysDictDataEntity>lambdaQuery().eq(SysDictDataEntity::getDictTypeId, vo.getDictTypeId())
						.eq(SysDictDataEntity::getDictValue, vo.getDictValue()));
//		if has dict
		if (sysDictData != null) {
//			throw exception
			throw new SwdaException("字典值重复!");
		}
//		convert to dict
		SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param dictVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysDictDataVO vo) {
//		get dict
		SysDictDataEntity sysDictData = getOne(Wrappers.<SysDictDataEntity>lambdaQuery()
				.eq(SysDictDataEntity::getDictTypeId, vo.getDictTypeId())
				.eq(SysDictDataEntity::getDictValue, vo.getDictValue()).ne(SysDictDataEntity::getId, vo.getId()));
//		if has dict
		if (sysDictData != null) {
//			throw exception
			throw new SwdaException("字典值重复!");
		}
//		convert to dict
		SysDictDataEntity entity = SysDictDataConvert.INSTANCE.convert(vo);
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