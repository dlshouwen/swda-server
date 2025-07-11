package com.dlshouwen.swda.bms.log.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlshouwen.swda.bms.log.convert.SmsLogConvert;
import com.dlshouwen.swda.bms.log.entity.SmsLog;
import com.dlshouwen.swda.bms.log.service.ISmsLogService;
import com.dlshouwen.swda.bms.log.vo.SmsLogVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

/**
 * sms log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/log/sms_log")
@Tag(name = "sms log")
@AllArgsConstructor
public class SmsLogController {
	
	/** sms log service */
	private final ISmsLogService smsLogService;

	/**
	 * get sms log page result
	 * @param query
	 * @return sms log page result
	 */
	@PostMapping("/page")
	@Operation(name = "get sms log page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:sms_log:page')")
	public R<PageResult<SmsLogVO>> getSmsLogPageResult(@RequestBody @Valid Query<SmsLog> query) {
//		get sms log page result
		PageResult<SmsLogVO> pageResult = smsLogService.getSmsLogPageResult(query);
//		return sms log page result
		return R.ok(pageResult);
	}

	/**
	 * get sms log data
	 * @param smsLogId
	 * @return sms log data
	 */
	@GetMapping("/{smsLogId}/data")
	@Operation(name = "get sms log data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:log:sms_log:data')")
	public R<SmsLogVO> getSmsLogData(@PathVariable("smsLogId") Long smsLogId) {
//		get sms log
		SmsLog smsLog = smsLogService.getById(smsLogId);
//		convert sms log to vo for return
		return R.ok(SmsLogConvert.INSTANCE.convert2VO(smsLog));
	}

	/**
	 * delete sms log
	 * @param smsLogIdList
	 * @return result
	 */
	@PostMapping("/delete")
	@Operation(name = "delete sms log", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:log:sms_log:delete')")
	public R<String> deleteSmsLog(@RequestBody List<Long> smsLogIdList) {
//		delete sms log
		smsLogService.deleteSmsLog(smsLogIdList);
//		return
		return R.ok();
	}

}