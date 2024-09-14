package com.dlshouwen.swda.bms.core.email.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.dlshouwen.swda.bms.core.email.cache.EmailCache;
import com.dlshouwen.swda.bms.core.email.param.AliyunEmailBatchSendParam;
import com.dlshouwen.swda.bms.core.email.param.AliyunEmailSendParam;
import com.dlshouwen.swda.bms.core.email.param.LocalEmailSendParam;
import com.dlshouwen.swda.bms.core.email.utils.AliyunEmailUtils;
import com.dlshouwen.swda.bms.core.email.utils.LocalEmailUtils;
import com.dlshouwen.swda.bms.log.entity.MailLog;
import com.dlshouwen.swda.bms.log.service.IMailLogService;
import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.bms.platform.service.IEmailPlatformService;
import com.dlshouwen.swda.core.common.exception.SwdaException;
import com.dlshouwen.swda.core.common.utils.ExceptionUtils;
import com.dlshouwen.swda.core.log.dict.CallResult;

import java.io.File;
import java.util.List;

/**
 * email service
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmailService {
	
	/** email platform service */
	private final IEmailPlatformService emailPlatformService;
	
	/** email cache */
	private final EmailCache emailCache;
	
	/** mail log service */
	private final IMailLogService mailLogService;

	/**
	 * send local
	 * @param param
	 * @return is success
	 */
	public boolean sendLocal(LocalEmailSendParam param) {
//		round email platform
		EmailPlatform emailPlatform = this.roundEmailPlatform(param.getGroupName());
//		send local
		return sendLocal(param, emailPlatform);
	}

	/**
	 * send local
	 * @param param
	 * @param emailPlatform
	 * @return is success
	 */
	public boolean sendLocal(LocalEmailSendParam param, EmailPlatform emailPlatform) {
		try {
//			send email
			new LocalEmailUtils(emailPlatform).sendEmail(param.getTos(), param.getSubject(), param.getContent(),
					param.isHtml(), ArrayUtil.toArray(param.getFiles(), File.class));
//			save mail log
			this.saveMailLog(emailPlatform.getId(), emailPlatform.getPlatform(), emailPlatform.getMailFrom(), param.getTos(), param.getSubject(),
					param.getContent(), null);
//			return true
			return true;
		} catch (Exception e) {
//			log error
			log.error("local mail send error: ", e);
//			save mail log
			this.saveMailLog(emailPlatform.getId(), emailPlatform.getPlatform(), emailPlatform.getMailFrom(), param.getTos(), param.getSubject(),
					param.getContent(), e);
//			return false
			return false;
		}
	}

	/**
	 * send aliyun
	 * @param param
	 * @return is success
	 */
	public boolean sendAliyun(AliyunEmailSendParam param) {
//		round email platform
		EmailPlatform emailPlatform = this.roundEmailPlatform(param.getGroupName());
//		send aliyun
		return sendAliyun(param, emailPlatform);
	}

	/**
	 * send aliyun
	 * @param param
	 * @param emailPlatform
	 * @return is success
	 */
	public boolean sendAliyun(AliyunEmailSendParam param, EmailPlatform emailPlatform) {
		try {
//			send email
			new AliyunEmailUtils(emailPlatform).sendEmail(param.getFrom(), param.getFormAlias(), param.getTos(),
					param.getSubject(), param.getContent(), param.isHtml());
//			save mail log
			this.saveMailLog(emailPlatform.getId(), emailPlatform.getPlatform(), param.getFrom(), param.getTos(), param.getSubject(),
					param.getContent(), null);
//			return true
			return true;
		} catch (Exception e) {
//			log error
			log.error("aliyun mail send error: ", e);
//			save mail log
			this.saveMailLog(emailPlatform.getId(), emailPlatform.getPlatform(), param.getFrom(), param.getTos(), param.getSubject(),
					param.getContent(), e);
//			return false
			return false;
		}
	}

	/**
	 * batch send aliyun
	 * @param param
	 * @return is success
	 */
	public boolean batchSendAliyun(AliyunEmailBatchSendParam param) {
//		round email platform
		EmailPlatform emailPlatform = this.roundEmailPlatform(param.getGroupName());
//		batch send aliyun
		return batchSendAliyun(param, emailPlatform);
	}

	/**
	 * batch send aliyun
	 * @param param
	 * @param emailPlatform
	 * @return is success
	 */
	public boolean batchSendAliyun(AliyunEmailBatchSendParam param, EmailPlatform emailPlatform) {
		try {
//			batch send email
			new AliyunEmailUtils(emailPlatform).batchSendEmail(param.getFrom(), param.getReceiversName(),
					param.getTemplateName(), param.getTagName());
//			save mail log
			this.saveMailLog(emailPlatform.getId(), emailPlatform.getPlatform(), param.getFrom(), param.getReceiversName(), null,
					param.getTemplateName(), null);
//			return true
			return true;
		} catch (Exception e) {
//			log error
			log.error("aliyun mail batch send error: ", e);
//			save mail log
			this.saveMailLog(emailPlatform.getId(), emailPlatform.getPlatform(), param.getFrom(), param.getReceiversName(), null,
					param.getTemplateName(), e);
//			return false
			return false;
		}
	}

	/**
	 * save mail log
	 * @param platformId
	 * @param platform
	 * @param mailFrom
	 * @param mailTos
	 * @param subject
	 * @param content
	 * @param e
	 */
	public void saveMailLog(Long platformId, Integer platform, String mailFrom, String mailTos, String subject, String content, Exception e) {
//		create mail log
		MailLog mailLog = new MailLog();
//		set platform id, platform, mail from, mail tos, subject, content
		mailLog.setPlatformId(platformId);
		mailLog.setPlatform(platform);
		mailLog.setMailFrom(mailFrom);
		mailLog.setMailTos(mailTos);
		mailLog.setSubject(subject);
		mailLog.setContent(content);
//		if has exception
		if (e != null) {
//			get message
			String message = StringUtils.substring(ExceptionUtils.toString(e), 0, 2000);
//			set status, error message
			mailLog.setStatus(CallResult.FAILURE);
			mailLog.setError(message);
		} else {
//			set status
			mailLog.setStatus(CallResult.SUCCESS);
		}
//		save mail log
		mailLogService.save(mailLog);
	}

	/**
	 * round email platform
	 * @param groupName
	 * @return email platform
	 */
	private EmailPlatform roundEmailPlatform(String groupName) {
//		get enable email platform list
		List<EmailPlatform> emailPlatformList = emailPlatformService.listByEnable();
//		if email platform list is empty
		if (emailPlatformList.isEmpty()) {
//			throw new exception
			throw new SwdaException("no email platform");
		}
//		if group name is blank
		if (StrUtil.isBlank(groupName)) {
//			get round
			long round = emailCache.getRoundValue();
//			get round email platform
			return emailPlatformList.get((int) round % emailPlatformList.size());
		}
//		filter group email platform list
		List<EmailPlatform> groupEmailPlatformList = emailPlatformList.stream().filter(platform -> StrUtil.equals(platform.getGroupName(), groupName)).toList();
//		if group email platform list is empty
		if (groupEmailPlatformList.isEmpty()) {
//			throw new exception
			throw new SwdaException("no group email platform");
		}
//		get round
		long round = emailCache.getRoundValue(groupName);
//		get round email platform
		return groupEmailPlatformList.get((int) round % groupEmailPlatformList.size());
	}

}
