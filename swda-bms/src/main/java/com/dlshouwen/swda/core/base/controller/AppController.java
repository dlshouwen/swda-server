package com.dlshouwen.swda.core.base.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;
import com.dlshouwen.swda.bms.system.convert.DictConvert;
import com.dlshouwen.swda.bms.system.convert.DictTypeConvert;
import com.dlshouwen.swda.bms.system.entity.Dict;
import com.dlshouwen.swda.bms.system.entity.DictType;
import com.dlshouwen.swda.bms.system.service.IAttrService;
import com.dlshouwen.swda.bms.system.service.IDictService;
import com.dlshouwen.swda.bms.system.service.IDictTypeService;
import com.dlshouwen.swda.bms.system.vo.AttrVO;
import com.dlshouwen.swda.bms.system.vo.DictTypeVO;
import com.dlshouwen.swda.bms.system.vo.DictVO;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	
	/** attr service */
	private final IAttrService attrService;
	
	/** dict type service */
	private final IDictTypeService dictTypeService;
	
	/** dict service */
	private final IDictService dictService;
	
	/**
	 * get attr data
	 * @return attr data
	 */
	@GetMapping("/attr")
	@Operation(name = "get attr data", type = OperateType.SEARCH)
	public R<List<AttrVO>> getAttrDataForApp() {
//		get attr list
		List<AttrVO> attrList = attrService.getAttrList();
//		return attr list
		return R.ok(attrList);
	}

	/**
	 * get dict data for app
	 * @return dict data
	 */
	@GetMapping("/dict")
	@Operation(name = "get dict data", type = OperateType.SEARCH)
	public R<Object> getDictData() {
//		get dict type list
		List<DictType> dictTypeList = dictTypeService.list();
//		get dict list
		List<Dict> dictList = dictService.list();
//		convert to vo list
		List<DictTypeVO> dictTypeVoList = DictTypeConvert.INSTANCE.convert2VOList(dictTypeList);
		List<DictVO> dictVoList = DictConvert.INSTANCE.convert2VOList(dictList);
//		return dict data
		return R.ok().data("dictTypeList", dictTypeVoList).data("dictList", dictVoList);
	}

}
