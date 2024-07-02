package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fhs.trans.service.impl.DictionaryTransService;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.convert.DictCategoryConvert;
import com.dlshouwen.swda.bms.mapper.DictMapper;
import com.dlshouwen.swda.bms.mapper.DictCategoryMapper;
import com.dlshouwen.swda.bms.entity.Dict;
import com.dlshouwen.swda.bms.entity.DictCategory;
import com.dlshouwen.swda.bms.enums.DictSourceEnum;
import com.dlshouwen.swda.bms.query.SysDictTypeQuery;
import com.dlshouwen.swda.bms.service.SysDictTypeService;
import com.dlshouwen.swda.bms.vo.DictCategoryVO;
import com.dlshouwen.swda.bms.vo.SysDictVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class SysDictTypeServiceImpl extends BaseServiceImpl<DictCategoryMapper, DictCategory> implements SysDictTypeService, InitializingBean {
	
	/** dict mapper */
	private final DictMapper sysDictDataDao;
	
	/** dict trans service */
	private final DictionaryTransService dictionaryTransService;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<DictCategoryVO> page(SysDictTypeQuery query) {
		IPage<DictCategory> page = baseMapper.selectPage(getPage(query), getWrapper(query));
		return new PageResult<>(DictCategoryConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private Wrapper<DictCategory> getWrapper(SysDictTypeQuery query) {
//		create wrapper
		LambdaQueryWrapper<DictCategory> wrapper = new LambdaQueryWrapper<>();
//		set condition
		wrapper.like(StrUtil.isNotBlank(query.getDictType()), DictCategory::getDictType, query.getDictType());
		wrapper.like(StrUtil.isNotBlank(query.getDictName()), DictCategory::getDictName, query.getDictName());
		wrapper.orderByAsc(DictCategory::getSort);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param dictTypeVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(DictCategoryVO vo) {
//		convert to dict type
		DictCategory entity = DictCategoryConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
	}

	/**
	 * update
	 * @param dictTypeVO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(DictCategoryVO vo) {
//		convert to dict type
		DictCategory entity = DictCategoryConvert.INSTANCE.convert(vo);
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

	/**
	 * get dict sql
	 * @param id
	 * @return dict list
	 */
	@Override
	public List<SysDictVO.DictData> getDictSql(Long id) {
//		get dict type
		DictCategory entity = this.getById(id);
//		try catch
		try {
//			get dict list for return
			return sysDictDataDao.getListForSql(entity.getDictSql());
		} catch (Exception e) {
//			throw exception
			throw new SwdaException("动态SQL执行失败，请检查SQL是否正确！");
		}
	}

	/**
	 * get dict list
	 * @return dict list
	 */
	@Override
	public List<SysDictVO> getDictList() {
//		get dict type list
		List<DictCategory> typeList = this.list(Wrappers.emptyWrapper());
//		get dict list
		QueryWrapper<Dict> query = new QueryWrapper<Dict>().orderByAsc("sort");
		List<Dict> dataList = sysDictDataDao.selectList(query);
//		defined dict list
		List<SysDictVO> dictList = new ArrayList<>(typeList.size());
//		for each type
		for (DictCategory type : typeList) {
//			defined dict
			SysDictVO dict = new SysDictVO();
//			set dict type
			dict.setDictType(type.getDictType());
//			for each dict
			for (Dict data : dataList) {
//				is this type
				if (type.getId().equals(data.getDictTypeId())) {
//					add dict list
					dict.getDataList().add(new SysDictVO.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass()));
				}
			}
//			if sql dict
			if (type.getDictSource() == DictSourceEnum.SQL.getValue()) {
//				get sql
				String sql = type.getDictSql();
//				try catch
				try {
//					get dict list
					dict.setDataList(sysDictDataDao.getListForSql(sql));
				} catch (Exception e) {
//					log error
					log.error("增加动态字典异常: type=" + type, e);
				}
			}
//			add dict
			dictList.add(dict);
		}
//		return dict list
		return dictList;
	}

	/**
	 * after properties set
	 */
	@Override
	public void afterPropertiesSet() {
//		refresh trans cache
		refreshTransCache();
	}

	/**
	 * refresh trans cache
	 */
	public void refreshTransCache() {
//		async
		CompletableFuture.supplyAsync(() -> {
//			get dict list
			List<Dict> dataList = sysDictDataDao.selectList(new LambdaQueryWrapper<>());
//			group by dict type
			Map<Long, List<Dict>> dictTypeDataMap = dataList.stream().collect(Collectors.groupingBy(Dict::getDictTypeId));
//			get dict type list
			List<DictCategory> dictTypeEntities = super.list();
//			for each dict type
			for (DictCategory dictTypeEntity : dictTypeEntities) {
//				contains key
				if (dictTypeDataMap.containsKey(dictTypeEntity.getId())) {
//					try catch
					try {
//						refresh cache
						dictionaryTransService.refreshCache(dictTypeEntity.getDictType(),
								dictTypeDataMap.get(dictTypeEntity.getId()).stream().collect(Collectors
										.toMap(Dict::getDictValue, Dict::getDictLabel)));
					} catch (Exception e) {
//						log error
						log.error("刷新字典缓存异常: type=" + dictTypeEntity, e);
					}
				}
			}
			return null;
		});
	}

}
