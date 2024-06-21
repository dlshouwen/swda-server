package com.dlshouwen.swda.core.loader;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dlshouwen.swda.core.entity.base.Data;
import com.dlshouwen.swda.core.property.UniqueProperties;

import cn.hutool.core.map.MapUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * data loader
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class DataLoader {

	/**
	 * load
	 * @param uniqueProperties
	 * @param template
	 */
	public static void load(UniqueProperties uniqueProperties, JdbcTemplate template) {
		/*
		 * 1. get attr
		 */
//		defined attr
		Map<String, String> attr = new HashMap<>();
//		get attr list
		List<Map<String, Object>> attrList = template .queryForList("select attr_id, content from bms_attr order by sort");
//		set attrs to  map
		for (Map<String, Object> _attr : attrList) {
			attr.put(MapUtil.getStr(_attr, "attr_id"), MapUtil.getStr(_attr, "content"));
		}
//		set to data
		Data.attr = attr;
//		set version
		Data.version = MapUtil.getStr(Data.attr, "version");
		/*
		 * 2. get dict
		 */
//		defined dict
		Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
//		get dict list
		List<Map<String, Object>> dictList = template .queryForList("select * from bms_dict order by dict_category_id, sort");
//		set dicts to dict
		Map<Integer, String> dictCategoryMap = null;
		String dictCategoryId;
		for (Map<String, Object> nowDict : dictList) {
			dictCategoryId = MapUtil.getStr(nowDict, "dict_category_id");
			if (dict.containsKey(dictCategoryId)) {
				dictCategoryMap = dict.get(dictCategoryId);
				dictCategoryMap.put(MapUtil.getInt(nowDict, "dict_key"), MapUtil.getStr(nowDict, "dict_value"));
			} else {
				dictCategoryMap = new LinkedHashMap<>();
				dictCategoryMap.put(MapUtil.getInt(nowDict, "dict_key"), MapUtil.getStr(nowDict, "dict_value"));
				dict.put(dictCategoryId, dictCategoryMap);
			}
		}
//		set to data
		Data.dict = dict;
//		set unique to data
		Data.unique = uniqueProperties.getUniques();
	}

}
