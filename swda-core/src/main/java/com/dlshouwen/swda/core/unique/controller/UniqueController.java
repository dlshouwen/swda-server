package com.dlshouwen.swda.core.unique.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlshouwen.swda.core.common.entity.Data;
import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.jdbc.dao.BaseDao;
import com.dlshouwen.swda.core.unique.entity.ValidParam;

import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * unique
 * @author liujingcheng@live.cn
 * @since 1.0.0
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
//		attrs is empty
		if(validParam.getAttrs()==null||validParam.getAttrs().size()==0){
//			query count
			count = dao.queryForObject(sql, Integer.class, validParam.getKey());
		}else{
//			add to key
			validParam.getAttrs().add(0, validParam.getKey());
//			query count
			count = dao.queryForObject(sql, Integer.class, validParam.getAttrs().toArray());
		}
//		return
		return R.ok(count==0);
	}

}
