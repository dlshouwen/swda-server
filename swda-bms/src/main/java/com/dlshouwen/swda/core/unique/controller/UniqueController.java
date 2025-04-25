package com.dlshouwen.swda.core.unique.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlshouwen.swda.core.unique.entity.ValidParam;
import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.jdbc.dao.BaseDao;

import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * unique
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/unique")
@AllArgsConstructor
@Tag(name = "unique")
public class UniqueController {
	
	/** dao */
	private final BaseDao dao;
	
	/**
	 * unique
	 * @param validParam
	 * @return result
	 */
	@PostMapping(value="")
	public R<Boolean> unique(@RequestBody ValidParam validParam) {
//		get sql
		String sql = MapUtil.getStr(Data.unique, validParam.getCode());
//		defined count
		int count = 0;
//		args is empty
		if(validParam.getArgs()==null||validParam.getArgs().size()==0){
//			query count
			count = dao.queryForObject(sql, Integer.class, validParam.getValue());
		}else{
//			add to key
			validParam.getArgs().add(0, validParam.getValue());
			System.out.println(validParam.getArgs());
//			query count
			count = dao.queryForObject(sql, Integer.class, validParam.getArgs().toArray());
		}
//		return
		return R.ok(count==0);
	}

}
