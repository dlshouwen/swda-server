package com.dlshouwen.swda.bms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import com.dlshouwen.swda.core.utils.JsonUtils;
import com.dlshouwen.swda.core.entity.base.PageResult;
import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.core.service.impl.BaseServiceImpl;
import com.dlshouwen.swda.bms.cache.AttrCache;
import com.dlshouwen.swda.bms.convert.SysParamsConvert;
import com.dlshouwen.swda.bms.mapper.SysParamsDao;
import com.dlshouwen.swda.bms.entity.Attr;
import com.dlshouwen.swda.bms.query.SysParamsQuery;
import com.dlshouwen.swda.bms.service.SysParamsService;
import com.dlshouwen.swda.bms.vo.AttrVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * params service impl
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
public class SysParamsServiceImpl extends BaseServiceImpl<SysParamsDao, Attr> implements SysParamsService {
	
	/** params cache */
	private final AttrCache sysParamsCache;

	/**
	 * page
	 * @param query
	 * @return page result
	 */
	@Override
	public PageResult<AttrVO> page(SysParamsQuery query) {
//		select page
		IPage<Attr> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//		get page result for return
		return new PageResult<>(SysParamsConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
	}

	/**
	 * get wrapper
	 * @param query
	 * @return wrapper
	 */
	private LambdaQueryWrapper<Attr> getWrapper(SysParamsQuery query) {
//		get wrapper
		LambdaQueryWrapper<Attr> wrapper = Wrappers.lambdaQuery();
//		set condition
		wrapper.like(StrUtil.isNotBlank(query.getParamKey()), Attr::getParamKey, query.getParamKey());
		wrapper.eq(StrUtil.isNotBlank(query.getParamValue()), Attr::getParamValue, query.getParamValue());
		wrapper.eq(query.getParamType() != null, Attr::getParamType, query.getParamType());
		wrapper.orderByDesc(Attr::getId);
//		return wrapper
		return wrapper;
	}

	/**
	 * save
	 * @param paramsVO
	 */
	@Override
	public void save(AttrVO vo) {
//		get is exist
		boolean exist = baseMapper.isExist(vo.getParamKey());
//		if exist
		if (exist) {
//			throw exception
			throw new SwdaException("参数键已存在");
		}
//		convert to params
		Attr entity = SysParamsConvert.INSTANCE.convert(vo);
//		insert
		baseMapper.insert(entity);
//		save cache
		sysParamsCache.save(entity.getParamKey(), entity.getParamValue());
	}

	/**
	 * update
	 * @param paramsVO
	 */
	@Override
	public void update(AttrVO vo) {
//		get params
		Attr entity = baseMapper.selectById(vo.getId());
//		if has change
		if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
//			is key exist
			boolean exist = baseMapper.isExist(vo.getParamKey());
//			if exist
			if (exist) {
//				throw exception
				throw new SwdaException("参数键已存在");
			}
//			delete cache
			sysParamsCache.delete(entity.getParamKey());
		}
//		update
		updateById(SysParamsConvert.INSTANCE.convert(vo));
//		save cache
		sysParamsCache.save(vo.getParamKey(), vo.getParamValue());
	}

	/**
	 * delete
	 * @param idList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(List<Long> idList) {
//		get params list
		List<Attr> list = baseMapper.selectBatchIds(idList);
//		remove
		removeByIds(idList);
//		get delete keys
		Object[] keys = list.stream().map(Attr::getParamKey).toArray(String[]::new);
//		delete cache
		sysParamsCache.delete(keys);
	}

	/**
	 * get string
	 * @param key
	 * @return value
	 */
	@Override
	public String getString(String paramKey) {
//		get value
		String value = sysParamsCache.get(paramKey);
//		if not blank
		if (StrUtil.isNotBlank(value)) {
//			return value
			return value;
		}
//		if cache emptyt then get from database
		Attr entity = baseMapper.get(paramKey);
//		if params is empty
		if (entity == null) {
//			throw exception
			throw new SwdaException("参数值不存在，paramKey：" + paramKey);
		}
//		save cache
		sysParamsCache.save(entity.getParamKey(), entity.getParamValue());
//		return value
		return entity.getParamValue();
	}

	/**
	 * get int
	 * @param key
	 * @return value
	 */
	@Override
	public int getInt(String paramKey) {
		return Integer.parseInt(getString(paramKey));
	}

	/**
	 * get boolean
	 * @param key
	 * @return value
	 */
	@Override
	public boolean getBoolean(String paramKey) {
		return Boolean.parseBoolean(getString(paramKey));
	}

	/**
	 * get json object
	 * @param key
	 * @return value
	 */
	@Override
	public <T> T getJSONObject(String paramKey, Class<T> valueType) {
		return JsonUtils.parseObject(getString(paramKey), valueType);
	}

}