package com.dlshouwen.swda.bms.platform.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dlshouwen.swda.bms.core.sms.service.SmsService;
import com.dlshouwen.swda.bms.platform.convert.SmsPlatformConvert;
import com.dlshouwen.swda.bms.platform.entity.SmsPlatform;
import com.dlshouwen.swda.bms.platform.service.ISmsPlatformService;
import com.dlshouwen.swda.bms.platform.vo.SmsPlatformVO;
import com.dlshouwen.swda.bms.platform.vo.SmsSendVO;
import com.dlshouwen.swda.core.common.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * sms platform
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/platform/sms_platform")
@Tag(name = "sms platform")
@AllArgsConstructor
public class SmsPlatformController {
	
	/** sms platform service */
	private final ISmsPlatformService smsPlatformService;
	
	/** sms service */
	private final SmsService smsService;

	@GetMapping("/page")
	@Operation(name = "get sms platform page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:platform:sms_platform')")
	public R<PageResult<SmsPlatformVO>> getSmsPlatformPageResult(@ParameterObject @Valid Query<SmsPlatform> query) {
//		get sms platform page result
		PageResult<SmsPlatformVO> pageResult = smsPlatformService.getSmsPlatformPageResult(query);
//		return sms platform page result
		return R.ok(pageResult);
	}

	@GetMapping("/list")
	@Operation(name = "get sms platform list", type = OperateType.SEARCH)
	public R<List<SmsPlatformVO>> getSmsPlatformList(Integer smsPlatformType) {
//		get sms platform list
		List<SmsPlatformVO> smsPlatformList = smsPlatformService.getSmsPlatformList(smsPlatformType);
//		return sms platform list
		return R.ok(smsPlatformList);
	}

	@GetMapping("/data/{smsPlatformId}")
	@Operation(name = "get sms platform data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:platform:sms_platform')")
	public R<SmsPlatformVO> get(@PathVariable("smsPlatformId") Long smsPlatformId) {
//		get sms platform
		SmsPlatform smsPlatform = smsPlatformService.getById(smsPlatformId);
//		convert to sms platform vo for return
		return R.ok(SmsPlatformConvert.INSTANCE.convert(smsPlatform));
	}

	@PostMapping("/add")
	@Operation(name = "add sms platform", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:platform:sms_platform:add')")
	public R<String> addSmsPlatform(@RequestBody SmsPlatformVO smsPlatformVO) {
//		add sms platform
		smsPlatformService.addSmsPlatform(smsPlatformVO);
//		return
		return R.ok();
	}

	@PutMapping("/update")
	@Operation(name = "update sms platform", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:platform:sms_platform:update')")
	public R<String> updateSmsPlatform(@RequestBody @Valid SmsPlatformVO smsPlatformVO) {
//		update sms platform
		smsPlatformService.updateSmsPlatform(smsPlatformVO);
//		return
		return R.ok();
	}

	@DeleteMapping("/delete")
	@Operation(name = "delete sms platform", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:platform:sms_platform:delete')")
	public R<String> deleteSmsPlatform(@RequestBody List<Long> smsPlatformIdList) {
//		delete sms platform
		smsPlatformService.deleteSmsPlatform(smsPlatformIdList);
//		return
		return R.ok();
	}

	@PostMapping("/send")
	@Operation(name = "send sms", type = OperateType.OTHER)
	@PreAuthorize("hasAuthority('bms:platform:sms_platform:send')")
	public R<String> sendSms(@RequestBody SmsSendVO smsSendVO) {
//		get sms platform
		SmsPlatform smsPlatform = smsPlatformService.getById(smsSendVO.getSmsPlatformId());
//		create params
		Map<String, String> params = new LinkedHashMap<>();
//		if has param
		if (StringUtils.isNotBlank(smsSendVO.getParamValue())) {
//			set param value to params
			params.put(smsSendVO.getParamKey(), smsSendVO.getParamValue());
		}
//		send message
		boolean result = smsService.send(smsPlatform, smsSendVO.getMobile(), params);
//		return
		return result?R.ok():R.error("send message error");
	}

}