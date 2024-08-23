package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.exception.SwdaException;
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
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {

	/**
	 * get dict list
	 * @param query
	 * @return dict list
	 */
	@Override
	public PageResult<DictVO> getDictList(Query<Dict> query) {
//		query page
		IPage<Dict> page = this.page(query);
//		convert to vo list for return
		return new PageResult<>(DictConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get dict data
	 * @param dictCategoryId
	 * @param dictId
	 * @return dict
	 */
	@Override
	public DictVO getDictData(String dictCategoryId, String dictId) {
//		get dict
		Dict dict = this.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCategoryId, dictCategoryId).eq(Dict::getDictId, dictId));
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
		Dict dict = this.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCategoryId, dictVO.getDictCategoryId()).eq(Dict::getDictId, dictVO.getDictId()));
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
		Dict dict = this.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCategoryId, dictVO.getDictCategoryId()).eq(Dict::getDictId, dictVO.getDictId()));
//		if has dict
		if (dict != null) {
//			throw exception
			throw new SwdaException("字典值重复!");
		}
//		convert to dict
		dict = DictConvert.INSTANCE.convert(dictVO);
//		update
		this.update(dict, Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCategoryId, dictVO.getDictCategoryId()).eq(Dict::getDictId, dictVO.getDictId()));
	}

	/**
	 * delete dict
	 * @param dictCategoryId
	 * @param dictIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDict(String dictCategoryId, List<String> dictIdList) {
//		delete dict
		for(String dictId : dictIdList) {
			this.remove(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCategoryId, dictCategoryId).eq(Dict::getDictId, dictId));
		}
	}

}