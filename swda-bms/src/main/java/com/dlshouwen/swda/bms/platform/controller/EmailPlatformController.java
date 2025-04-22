package com.dlshouwen.swda.bms.platform.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dlshouwen.swda.core.email.param.AliyunEmailBatchSendParam;
import com.dlshouwen.swda.core.email.param.AliyunEmailSendParam;
import com.dlshouwen.swda.core.email.param.LocalEmailSendParam;
import com.dlshouwen.swda.core.email.service.EmailService;
import com.dlshouwen.swda.bms.platform.convert.EmailPlatformConvert;
import com.dlshouwen.swda.bms.platform.dict.EmailFormatType;
import com.dlshouwen.swda.bms.platform.dict.EmailPlatformType;
import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.bms.platform.service.IEmailPlatformService;
import com.dlshouwen.swda.bms.platform.vo.EmailPlatformVO;
import com.dlshouwen.swda.bms.platform.vo.EmailSendVO;
import com.dlshouwen.swda.core.base.entity.R;
import com.dlshouwen.swda.core.grid.dto.PageResult;
import com.dlshouwen.swda.core.grid.dto.Query;
import com.dlshouwen.swda.core.log.annotation.Operation;
import com.dlshouwen.swda.core.log.enums.OperateType;

import java.util.List;

/**
 * email platform
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bms/platform/email_platform")
@Tag(name = "email platform")
@AllArgsConstructor
public class EmailPlatformController {
	
	/** email platform service */
	private final IEmailPlatformService emailPlatformService;
	
	/** email service */
	private final EmailService emailService;

	/**
	 * get email platform page result
	 * @param query
	 * @return email platform page result
	 */
	@PostMapping("/page")
	@Operation(name = "get email platform page result", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:platform:email_platform:page')")
	public R<PageResult<EmailPlatformVO>> getEmailPlatformPageResult(@RequestBody @Valid Query<EmailPlatform> query) {
//		get email platform page result
		PageResult<EmailPlatformVO> pageResult = emailPlatformService.getEmailPlatformPageResult(query);
//		return email platform page result
		return R.ok(pageResult);
	}

	/**
	 * get email platform list
	 * @param emailPlatformType
	 * @return email platform list
	 */
	@PostMapping("/list")
	@Operation(name = "get email platform list", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:platform:email_platform:list')")
	public R<List<EmailPlatformVO>> getEmailPlatformList(String emailPlatformType) {
//		get email platform list
		List<EmailPlatformVO> emailPlatformList = emailPlatformService.getEmailPlatformList(emailPlatformType);
//		return email platform list
		return R.ok(emailPlatformList);
	}

	/**
	 * get email platform data
	 * @param emailPlatformId
	 * @return email platform data
	 */
	@GetMapping("/{emailPlatformId}/data")
	@Operation(name = "get email platform data", type = OperateType.SEARCH)
	@PreAuthorize("hasAuthority('bms:platform:email_platform:data')")
	public R<EmailPlatformVO> getEmailPlatformData(@PathVariable("emailPlatformId") Long emailPlatformId) {
//		get email platform
		EmailPlatform emailPlatform = emailPlatformService.getById(emailPlatformId);
//		convert to vo for return
		return R.ok(EmailPlatformConvert.INSTANCE.convert2VO(emailPlatform));
	}

	/**
	 * add email platform
	 * @param emailPlatformVO
	 * @return result
	 */
	@PostMapping("/add")
	@Operation(name = "add email platform", type = OperateType.INSERT)
	@PreAuthorize("hasAuthority('bms:platform:email_platform:add')")
	public R<String> addEmailPlatform(@RequestBody EmailPlatformVO emailPlatformVO) {
//		add email platform
		emailPlatformService.addEmailPlatform(emailPlatformVO);
//		return
		return R.ok();
	}

	/**
	 * update email platform
	 * @param emailPlatformVO
	 * @return result
	 */
	@PutMapping("/update")
	@Operation(name = "update email platform", type = OperateType.UPDATE)
	@PreAuthorize("hasAuthority('bms:platform:email_platform:update')")
	public R<String> updateEmailPlatform(@RequestBody @Valid EmailPlatformVO emailPlatformVO) {
//		update email platform
		emailPlatformService.updateEmailPlatform(emailPlatformVO);
//		return
		return R.ok();
	}

	/**
	 * delete email platform
	 * @param emailPlatformIdList
	 * @return result
	 */
	@DeleteMapping("/delete")
	@Operation(name = "delete email platform", type = OperateType.DELETE)
	@PreAuthorize("hasAuthority('bms:platform:email_platform:delete')")
	public R<String> deleteEmailPlatform(@RequestBody List<Long> emailPlatformIdList) {
//		delete email platform
		emailPlatformService.deleteEmailPlatform(emailPlatformIdList);
//		return
		return R.ok();
	}

	/**
	 * send email
	 * @param mailSend
	 * @return result
	 */
	@PostMapping("/send")
	@Operation(name = "send email", type = OperateType.OTHER)
	public R<String> sendEmail(@RequestBody EmailSendVO emailSendVO) {
//		get email platform
		EmailPlatform emailPlatform = emailPlatformService.getById(emailSendVO.getEmailPlatformId());
//		local email
		if (emailSendVO.getEmailPlatformType() == EmailPlatformType.LOCAL) {
//			create local email send param
			LocalEmailSendParam param = new LocalEmailSendParam();
//			set tos, subject, content, html
			param.setTos(emailSendVO.getMailTos());
			param.setSubject(emailSendVO.getSubject());
			param.setContent(emailSendVO.getContent());
			param.setHtml(emailSendVO.getEmailFormatType()==EmailFormatType.HTML);
//			send local
			boolean result = emailService.sendLocal(param, emailPlatform);
//			return
			return result?R.ok():R.error("send mail error");
		}
//		aliyun email
		if (emailSendVO.getEmailPlatformType() == EmailPlatformType.ALIYUN) {
//			normal
			if(emailSendVO.getEmailFormatType()!=EmailFormatType.TEMPLATE) {
//				create aliyun email send param
				AliyunEmailSendParam param = new AliyunEmailSendParam();
//				set from, from alias, tos, subject, content, html
				param.setFrom(emailSendVO.getMailFrom());
				param.setFormAlias(emailSendVO.getFormAlias());
				param.setTos(emailSendVO.getMailTos());
				param.setSubject(emailSendVO.getSubject());
				param.setContent(emailSendVO.getContent());
				param.setHtml(emailSendVO.getEmailFormatType()==EmailFormatType.HTML);
//				send aliyun
				boolean result = emailService.sendAliyun(param, emailPlatform);
//				return
				return result?R.ok():R.error("send mail error");
			}
//			template
			if(emailSendVO.getEmailFormatType()==EmailFormatType.TEMPLATE) {
//				create aliyun email batch send param
				AliyunEmailBatchSendParam param = new AliyunEmailBatchSendParam();
//				set from, receivers name, tag name, template name
				param.setFrom(emailSendVO.getMailFrom());
				param.setReceiversName(emailSendVO.getReceiversName());
				param.setTagName(emailSendVO.getTagName());
				param.setTemplateName(emailSendVO.getTemplateName());
//				batch send aliyun
				boolean result = emailService.batchSendAliyun(param, emailPlatform);
//				return
				return result?R.ok():R.error("send mail error");
			}
		}
//		return
		return R.error("no support");
	}

}