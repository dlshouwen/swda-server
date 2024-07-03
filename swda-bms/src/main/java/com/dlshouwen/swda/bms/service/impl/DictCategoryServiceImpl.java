package com.dlshouwen.swda.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fhs.trans.service.impl.DictionaryTransService;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.grid.PageResult;
import com.dlshouwen.swda.core.entity.grid.Query;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.core.utils.GridUtils;
import com.dlshouwen.swda.bms.convert.DictCategoryConvert;
import com.dlshouwen.swda.bms.convert.DictConvert;
import com.dlshouwen.swda.bms.mapper.DictMapper;
import com.dlshouwen.swda.bms.mapper.DictCategoryMapper;
import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.entity.DictCategory;
import com.dlshouwen.swda.bms.service.IDictCategoryService;
import com.dlshouwen.swda.bms.vo.DictCategoryVO;
import com.dlshouwen.swda.bms.vo.DictVO;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * dict category service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class DictCategoryServiceImpl extends BaseServiceImpl<DictCategoryMapper, DictCategory> implements IDictCategoryService, InitializingBean {
	
	/** dict mapper */
	private final DictMapper dictMapper;
	
	/** dict trans service */
	private final DictionaryTransService dictionaryTransService;

	/**
	 * get dict category list
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<DictCategoryVO> getDictCategoryList(Query<DictCategory> query) {
//		query page
		IPage<DictCategory> page = GridUtils.query(baseMapper, query);
//		return page result
		return new PageResult<>(DictCategoryConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get dict category data
	 * @param dictCategoryId
	 * @return dict category
	 */
	@Override
	public DictCategoryVO getDictCategoryData(String dictCategoryId) {
//		get dict category
		DictCategory dictCategory = this.getById(dictCategoryId);
//		convert to vo for return
		return DictCategoryConvert.INSTANCE.convert2VO(dictCategory);
	}

	/**
	 * add dict category
	 * @param dictCategoryVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addDictCategory(DictCategoryVO dictCategoryVO) {
//		convert to dict category
		DictCategory dictCategory = DictCategoryConvert.INSTANCE.convert(dictCategoryVO);
//		insert dict category
		baseMapper.insert(dictCategory);
	}

	/**
	 * update dict category
	 * @param dictTypeVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDictCategory(DictCategoryVO dictCategoryVO) {
//		convert to dict category
		DictCategory dictCategory = DictCategoryConvert.INSTANCE.convert(dictCategoryVO);
//		update dict category
		updateById(dictCategory);
	}

	/**
	 * delete dict category
	 * @param dictCategoryIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDictCategory(List<Long> dictCategoryIdList) {
//		delete dict category
		removeByIds(dictCategoryIdList);
	}

	/**
	 * get sql dict list
	 * @param dictCategoryId
	 * @return sql dict list
	 */
	@Override
	public List<DictVO> getSqlDictList(String dictCategoryId) {
//		get dict category
		DictCategory dictCategory = this.getById(dictCategoryId);
//		try catch
		try {
//			get dict list
			List<Dict> dictList = dictMapper.getListForSql(dictCategory.getSourceSql());
//			convert to vo list for return
			return DictConvert.INSTANCE.convert2VOList(dictList);
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("动态SQL执行失败，请检查SQL是否正确！");
		}
	}

	/**
	 * after properties set
	 */
	@Override
	public void afterPropertiesSet() {
//		refresh dict trans cache
		refreshDictTransCache();
	}

	/**
	 * refresh dict trans cache
	 */
	public void refreshDictTransCache() {
//		async
		CompletableFuture.supplyAsync(() -> {
//			get dict list
			List<Dict> dataList = dictMapper.selectList(new LambdaQueryWrapper<>());
//			group by dict category
			Map<String, List<Dict>> dictCategoryInfo = dataList.stream().collect(Collectors.groupingBy(Dict::getDictCategoryId));
//			get dict category list
			List<DictCategory> dictCategoryList = super.list();
//			for each dict category
			for (DictCategory dictCategory : dictCategoryList) {
//				contains key
				if (dictCategoryInfo.containsKey(dictCategory.getDictCategoryId())) {
//					try catch
					try {
//						refresh cache
						dictionaryTransService.refreshCache(dictCategory.getDictCategoryId(),
								dictCategoryInfo.get(dictCategory.getDictCategoryId()).stream().collect(Collectors.toMap(Dict::getDictValue, Dict::getDictKey)));
					} catch (Exception e) {
//						log error
						log.error("refresh dict trans cache error: "+dictCategory, e);
					}
				}
			}
			return null;
		});
	}

}
