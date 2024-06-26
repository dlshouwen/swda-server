package com.dlshouwen.swda.bms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * index
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@RestController
public class IndexController {

	/**
	 * index
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		return "swda started!";
	}

}
