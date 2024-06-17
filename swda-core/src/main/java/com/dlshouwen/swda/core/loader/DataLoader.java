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
 * <p>数据加载器</p>
 * @author 大连首闻科技有限公司
 * @since 0.0.1-SNAPSHOT
 */
public class DataLoader {
	
    /**
     * 加载数据
     * @param template 数据源
     */
    public static void load(UniqueProperties uniqueProperties, JdbcTemplate template){
        /*
         * 1. 获取参数内容
         */
        // 定义参数
        Map<String, String> attr = new HashMap<>();
        // 处理参数
        List<Map<String, Object>> attrList = template.queryForList("select attr_id, content from bms_attr order by sort");
        for(Map<String, Object> _attr : attrList){
            attr.put(MapUtil.getStr(_attr, "attr_id"), MapUtil.getStr(_attr, "content"));
        }
        // 设置到参数中
        Data.attr = attr;
        // 设置版本号
        Data.version = MapUtil.getStr(Data.attr, "version");
        /*
         * 2. 获取字典内容
         */
        // 定义字典
        Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
        // 处理字典内容
        List<Map<String, Object>> dictList = template.queryForList("select * from bms_dict order by dict_category_id, sort");
        Map<Integer, String> dictCategoryMap = null;
        String dictCategoryId;
        for(Map<String, Object> nowDict : dictList){
            dictCategoryId = MapUtil.getStr(nowDict, "dict_category_id");
            if(dict.containsKey(dictCategoryId)){
                dictCategoryMap = dict.get(dictCategoryId);
                dictCategoryMap.put(MapUtil.getInt(nowDict, "dict_key"), MapUtil.getStr(nowDict, "dict_value"));
            }else{
                dictCategoryMap = new LinkedHashMap<>();
                dictCategoryMap.put(MapUtil.getInt(nowDict, "dict_key"), MapUtil.getStr(nowDict, "dict_value"));
                dict.put(dictCategoryId, dictCategoryMap);
            }
        }
        Data.dict = dict;
        Data.unique = uniqueProperties.getUniques();
    }

}
