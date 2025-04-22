package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.exception.SwdaException;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.system.convert.DictConvert;
import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.mapper.DictMapper;
import com.dlshouwen.swda.bms.system.service.IDictService;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * dict service impl
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Service
@AllArgsConstructor
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {

	/**
	 * get dict page result
	 * @param dictType
	 * @param query
	 * @return dict page result
	 */
	@Override
	public PageResult<DictVO> getDictPageResult(String dictType, Query<Dict> query) {
//		get wrapper
		QueryWrapper<Dict> wrapper = this.wrapper(query);
//		set dict type
		wrapper.eq("dict_type", dictType);
//		query page
		IPage<Dict> page = this.page(query.getPage(), wrapper);
//		convert to vo list for return
		return new PageResult<>(DictConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get dict data
	 * @param dictId
	 * @return dict
	 */
	@Override
	public DictVO getDictData(Long dictId) {
//		get dict
		Dict dict = this.getById(dictId);
//		convert to vo for return
		return DictConvert.INSTANCE.convert2VO(dict);
	}

	/**
	 * add dict
	 * @param dictVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addDict(DictVO dictVO) {
//		get dict
		Dict dict = this.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictType, dictVO.getDictType()).eq(Dict::getDictValue, dictVO.getDictValue()));
//		if has dict
		if (dict != null) {
//			throw exception
			throw new SwdaException("字典值重复!");
		}
//		convert to dict
		dict = DictConvert.INSTANCE.convert(dictVO);
//		insert dict
		this.save(dict);
	}

	/**
	 * update dict
	 * @param dictVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDict(DictVO dictVO) {
//		get dict
		Dict dict = this.getOne(Wrappers.<Dict>lambdaQuery().notIn(Dict::getDictId, dictVO.getDictId()).eq(Dict::getDictType, dictVO.getDictType()).eq(Dict::getDictValue, dictVO.getDictValue()));
//		if has dict
		if (dict != null) {
//			throw exception
			throw new SwdaException("字典值重复!");
		}
//		convert to dict
		dict = DictConvert.INSTANCE.convert(dictVO);
//		update
		this.save(dict);
	}

	/**
	 * delete dict
	 * @param dictIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDict(List<Long> dictIdList) {
//		delete dict
		this.removeByIds(dictIdList);
	}

}