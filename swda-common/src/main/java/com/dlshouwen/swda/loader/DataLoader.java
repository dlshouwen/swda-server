package com.dlshouwen.swda.loader;

import com.dlshouwen.swda.entity.base.Data;

import cn.hutool.core.map.MapUtil;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * data loader
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
public class DataLoader {

	/**
	 * load
	 * @param template
	 */
	public static void load(JdbcTemplate template){
		/*
		 * 1. attr
		 */
//		defined attr
		Map<String, String> attr = new HashMap<>();
//		get attr list
		List<Map<String, Object>> attrList = template.queryForList("select attr_id, content from bms_attr order by sort");
//		convert attr list to map 
		for(Map<String, Object> _attr : attrList){
			attr.put(MapUtil.getStr(_attr, "attr_id"), MapUtil.getStr(_attr, "content"));
		}
//		set attr
		Data.attr = attr;
//		set version
		Data.version = MapUtil.getStr(Data.attr, "version");
		/*
		 * 2. dict
		 */
//		defined dict
		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
//		get dict list
		List<Map<String, Object>> dictList = template.queryForList("select * from bms_dict order by dict_category_id, sort");
//		convert dict list to dict map
		Map<String, String> dictCategoryMap = null;
		String dictCategoryId = "";
		for(Map<String, Object> nowDict : dictList){
			dictCategoryId = MapUtil.getStr(nowDict, "dict_category_id");
			if(dict.containsKey(dictCategoryId)){
				dictCategoryMap = dict.get(dictCategoryId);
				dictCategoryMap.put(MapUtil.getStr(nowDict, "dict_key"), MapUtil.getStr(nowDict, "dict_value"));
			}else{
				dictCategoryMap = new LinkedHashMap<>();
				dictCategoryMap.put(MapUtil.getStr(nowDict, "dict_key"), MapUtil.getStr(nowDict, "dict_value"));
				dict.put(dictCategoryId, dictCategoryMap);
			}
		}
//		set dict
		Data.dict = dict;
	}

}
