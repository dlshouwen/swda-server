package com.dlshouwen.swda.core.base.loader;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.unique.properties.UniqueProperties;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
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
		load();
//		delay
		scheduledService.scheduleWithFixedDelay(() -> {
//			load
			load();
		}, 1, 10, TimeUnit.MINUTES);
	}

	/**
	 * load
	 */
	public void load() {
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
		Map<String, Map<String, Object>> dict = new HashMap<String, Map<String, Object>>();
//		get dict list
		List<Map<String, Object>> dictList = template.queryForList("select dict_id, dict_name, dict_type, dict_label, dict_value, dict_style from bms_dict order by dict_type, sort");
//		for each dict
		for (Map<String, Object> _dict : dictList) {
//			get dict type
			String dictType = MapUtil.getStr(_dict, "dict_type");
//			no key
			if (!dict.containsKey(dictType)) {
//				defined info
				Map<String, Object> info = new LinkedHashMap<String, Object>();
//				defined datas
				List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
//				set datas to info
				info.put("datas", datas);
//				set info
				dict.put(dictType, info);
			}
//			get id, name, label, value, style
			String id = MapUtil.getStr(_dict, "dict_id");
			String name = MapUtil.getStr(_dict, "dict_name");
			String label = MapUtil.getStr(_dict, "dict_label");
			String value = MapUtil.getStr(_dict, "dict_value");
			String style = MapUtil.getStr(_dict, "dict_style");
//			get info
			Map<String, Object> info = dict.get(dictType);
//			set data
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			data.put("name", name);
			data.put("label", label);
			data.put("value", value);
			data.put("style", style);
//			set data
			info.put(value, data);
//			get datas
			List<Map<String, Object>> datas = MapUtil.get(info, "datas", new TypeReference<List<Map<String, Object>>>() {});
//			add data
			datas.add(data);
//			put datas
			info.put("datas", datas);
//			set info
			dict.put(dictType, info);
		}
//		set to data
		Data.dict = dict;
//		set unique to data
		Data.unique = uniqueProperties.getUniques();
	}

}
