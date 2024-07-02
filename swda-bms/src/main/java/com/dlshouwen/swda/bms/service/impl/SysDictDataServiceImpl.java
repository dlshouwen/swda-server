package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.DictConvert;
import com.dlshouwen.swda.bms.mapper.SysDictDataDao;
import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.query.SysDictDataQuery;
import com.dlshouwen.swda.bms.service.SysDictDataService;
import com.dlshouwen.swda.bms.vo.DictVO;
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
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataDao, Dict> implements SysDictDataService {

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<DictVO> page(SysDictDataQuery query) {
//		select page
		IPage<Dict> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		return page result
		return new PageResult<>(DictConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private Wrapper<Dict> getWrapper(SysDictDataQuery query) {
//		create wrapper
		LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
//		set condition
		wrapper.eq(Dict::getDictTypeId, query.getDictTypeId());
		wrapper.orderByAsc(Dict::getSort);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param dictVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(DictVO vo) {
//		get dict
		Dict sysDictData = getOne(
				Wrappers.<Dict>lambdaQuery().eq(Dict::getDictTypeId, vo.getDictTypeId())
						.eq(Dict::getDictValue, vo.getDictValue()));
//		if has dict
		if (sysDictData != null) {
//			throw exception
			throw new SwdaException("字典值重复!");
		}
//		convert to dict
		Dict entity = DictConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param dictVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(DictVO vo) {
//		get dict
		Dict sysDictData = getOne(Wrappers.<Dict>lambdaQuery()
				.eq(Dict::getDictTypeId, vo.getDictTypeId())
				.eq(Dict::getDictValue, vo.getDictValue()).ne(Dict::getId, vo.getId()));
//		if has dict
		if (sysDictData != null) {
//			throw exception
			throw new SwdaException("字典值重复!");
		}
//		convert to dict
		Dict entity = DictConvert.INSTANCE.convert(vo);
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