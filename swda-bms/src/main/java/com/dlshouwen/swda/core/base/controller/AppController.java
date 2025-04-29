package com.dlshouwen.swda.core.base.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.Data;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * app
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/app")
@Tag(name = "app")
@AllArgsConstructor
public class AppController {

	/**
	 * get attr data
	 * @return attr data
	 */
	@GetMapping("/attr")
	@Operation(name = "get attr data", type = OperateType.SEARCH)
	public R<Map<String, String>> getAttrDataForApp() {
//		return attr
		return R.ok(Data.attr);
	}

	/**
	 * get dict data for app
	 * @return dict data
	 */
	@GetMapping("/dict")
	@Operation(name = "get dict data", type = OperateType.SEARCH)
	public R<Map<String, Map<String, Object>>> getDictDataForApp() {
//		return dict data
		return R.ok(Data.dict);
	}

}
