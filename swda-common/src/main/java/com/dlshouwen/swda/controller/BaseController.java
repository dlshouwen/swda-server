package com.dlshouwen.swda.controller;

import com.dlshouwen.swda.loader.DataLoader;

import cn.hutool.core.map.MapUtil;

import com.dlshouwen.swda.entity.base.Data;
import com.dlshouwen.swda.entity.base.ResultCode;
import com.dlshouwen.swda.exception.SwdaException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * base
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Tag(name="base", description = "base")
public class BaseController {

	/**
	 * template
	 */
	@Resource
	private JdbcTemplate template;

	/**
	 * valid unique
	 * @param code 唯一识别码
	 * @param message 消息
	 * @param args 参数
	 */
	public void validUnique(String code, String message, Object... args) {
//		if not contains than return
		if(!Data.unique.containsKey(code)){
			return;
		}
//		get sql
		String sql = MapUtil.getStr(Data.unique, code);
//		get count
		Integer count = template.queryForObject(sql, Integer.class, args);
//		if count>0 then throw exception
		if(count!=null&&count>0){
			throw new SwdaException(ResultCode.ERROR, message);
		}
	}

	/**
	 * reload data
	 */
	public void reloadData(){
//		load
		DataLoader.load(template);
	}

}
