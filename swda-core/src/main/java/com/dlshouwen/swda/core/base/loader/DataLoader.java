package com.dlshouwen.swda.core.base.loader;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.unique.properties.UniqueProperties;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * data loader
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Component
@AllArgsConstructor
public class DataLoader {
	
	/** unique properties */
	private final UniqueProperties uniqueProperties;
	
	/** jdbc template */
	private final JdbcTemplate template;

	/**
	 * run
	 */
	@PostConstruct
	public void run() {
//		create schedule executor
		ScheduledExecutorService scheduledService = ThreadUtil.createScheduledExecutor(1);
//		load
		load(uniqueProperties, template);
//		delay
		scheduledService.scheduleWithFixedDelay(() -> {
//			load
			load(uniqueProperties, template);
		}, 1, 10, TimeUnit.MINUTES);
	}

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
		List<Map<String, Object>> attrList = template.queryForList("select attr_id, content from bms_attr order by sort");
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
		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
//		get dict list
		List<Map<String, Object>> dictList = template.queryForList("select dict_type, dict_value, dict_label from bms_dict order by dict_type, sort");
//		set dicts to dict
		Map<String, String> dictCategoryMap = null;
		String dictCategoryId;
		for (Map<String, Object> nowDict : dictList) {
			dictCategoryId = MapUtil.getStr(nowDict, "dict_type");
			if (dict.containsKey(dictCategoryId)) {
				dictCategoryMap = dict.get(dictCategoryId);
				dictCategoryMap.put(MapUtil.getStr(nowDict, "dict_value"), MapUtil.getStr(nowDict, "dict_label"));
			} else {
				dictCategoryMap = new LinkedHashMap<>();
				dictCategoryMap.put(MapUtil.getStr(nowDict, "dict_value"), MapUtil.getStr(nowDict, "dict_label"));
				dict.put(dictCategoryId, dictCategoryMap);
			}
		}
//		set to data
		Data.dict = dict;
//		set unique to data
		Data.unique = uniqueProperties.getUniques();
	}

}
