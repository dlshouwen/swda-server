package com.dlshouwen.swda.bms.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fhs.trans.service.impl.DictionaryTransService;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.mybatis.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.system.convert.DictTypeConvert;
import com.dlshouwen.swda.bms.system.convert.DictConvert;
import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.entity.DictType;
import com.dlshouwen.swda.bms.system.mapper.DictTypeMapper;
import com.dlshouwen.swda.bms.system.mapper.DictMapper;
import com.dlshouwen.swda.bms.system.service.IDictTypeService;
import com.dlshouwen.swda.bms.system.vo.DictTypeVO;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * dict type service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeMapper, DictType> implements IDictTypeService, InitializingBean {
	
	/** dict mapper */
	private final DictMapper dictMapper;
	
	/** dict trans service */
	private final DictionaryTransService dictionaryTransService;

	/**
	 * get dict type list
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<DictTypeVO> getDictTypeList(Query<DictType> query) {
//		query page
		IPage<DictType> page = this.page(query);
//		return page result
		return new PageResult<>(DictTypeConvert.INSTANCE.convert2VOList(page.getRecords()), page.getTotal());
	}
	
	/**
	 * get dict type data
	 * @param dictTypeId
	 * @return dict type
	 */
	@Override
	public DictTypeVO getDictTypeData(Long dictTypeId) {
//		get dict type
		DictType dictType = this.getById(dictTypeId);
//		convert to vo for return
		return DictTypeConvert.INSTANCE.convert2VO(dictType);
	}

	/**
	 * add dict type
	 * @param dictTypeVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addDictType(DictTypeVO dictTypeVO) {
//		convert to dict type
		DictType dictType = DictTypeConvert.INSTANCE.convert(dictTypeVO);
//		insert dict type
		baseMapper.insert(dictType);
	}

	/**
	 * update dict type
	 * @param dictTypeVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDictType(DictTypeVO dictTypeVO) {
//		convert to dict type
		DictType dictType = DictTypeConvert.INSTANCE.convert(dictTypeVO);
//		update dict type
		updateById(dictType);
	}

	/**
	 * delete dict type
	 * @param dictTypeIdList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteDictType(List<Long> dictTypeIdList) {
//		delete dict type
		removeByIds(dictTypeIdList);
	}

	/**
	 * get sql dict list
	 * @param dictTypeId
	 * @return sql dict list
	 */
	@Override
	public List<DictVO> getSqlDictList(Long dictTypeId) {
//		get dict type
		DictType dictType = this.getById(dictTypeId);
//		try catch
		try {
//			get dict list
			List<Dict> dictList = dictMapper.getListForSql(dictType.getSourceSql());
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
			List<Dict> dataList = dictMapper.selectList(Wrappers.emptyWrapper());
//			group by dict type
			Map<String, List<Dict>> dictTypeInfo = dataList.stream().collect(Collectors.groupingBy(Dict::getDictType));
//			get dict type list
			List<DictType> dictTypeList = super.list();
//			for each dict type
			for (DictType dictType : dictTypeList) {
//				contains key
				if (dictTypeInfo.containsKey(dictType.getDictType())) {
//					try catch
					try {
//						refresh cache
						dictionaryTransService.refreshCache(dictType.getDictType(),
								dictTypeInfo.get(dictType.getDictType()).stream().collect(Collectors.toMap(Dict::getDictValue, Dict::getDictKey)));
					} catch (Exception e) {
//						log error
						log.error("refresh dict trans cache error: "+dictType, e);
					}
				}
			}
			return null;
		});
	}

}
