package com.dlshouwen.swda.common.email.utils;

import com.aliyun.dm20151123.Client;
import com.aliyun.dm20151123.models.BatchSendMailRequest;
import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.teaopenapi.models.Config;
import com.dlshouwen.swda.common.base.exception.SwdaException;
import com.dlshouwen.swda.common.email.entity.EmailPlatform;

import lombok.extern.slf4j.Slf4j;

/**
 * aliyun email utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
public class AliyunEmailUtils {

	/** client */
	private final Client client;

	public AliyunEmailUtils(EmailPlatform emailPlatform) throws Exception {
//		defined config
		Config config = new Config();
//		set end point, region id, access key id, access key secret
		config.setEndpoint(emailPlatform.getEndpoint());
		config.setRegionId(emailPlatform.getRegionId());
		config.setAccessKeyId(emailPlatform.getAccessKey());
		config.setAccessKeySecret(emailPlatform.getSecretKey());
//		create client
		this.client = new Client(config);
	}

	/**
	 * send email
	 * @param from
	 * @param formAlias
	 * @param tos
	 * @param subject
	 * @param content
	 * @param isHtml
	 * @return env-id
	 */
	public String sendEmail(String from, String formAlias, String tos, String subject, String content, boolean isHtml) {
//		create single send mail request
		SingleSendMailRequest request = new SingleSendMailRequest();
//		set account name, alias, address type(0-random, 1-send), reply to address, to, subject
		request.setAccountName(from);
		request.setFromAlias(formAlias);
		request.setAddressType(1);
		request.setReplyToAddress(true);
		request.setToAddress(tos);
		request.setSubject(subject);
//		set body
		if (isHtml) {
			request.setHtmlBody(content);
		} else {
			request.setTextBody(content);
		}
//		send click trace, 0-close, 1-open
		request.setClickTrace("0");
//		try catch
		try {
//			send email
			return client.singleSendMail(request).getBody().getEnvId();
		} catch (Exception e) {
//			log error
			log.error("aliyun email send error: ", e);
//			throw new exception
			throw new SwdaException(e.getMessage());
		}
	}

	/**
	 * batch send email
	 * @param from
	 * @param receiversName
	 * @param templateName
	 * @param tagName
	 * @return env-id
	 */
	public String batchSendEmail(String from, String receiversName, String templateName, String tagName) {
//		create batch send mail request
		BatchSendMailRequest request = new BatchSendMailRequest();
//		set account name, receivers name, template name, address type, target name
		request.setAccountName(from);
		request.setReceiversName(receiversName);
		request.setTemplateName(templateName);
		request.setAddressType(1);
		request.setTagName(tagName);
//		send click trace, 0-close, 1-open
		request.setClickTrace("0");
//		try catch
		try {
//			batch send email
			return client.batchSendMail(request).getBody().getEnvId();
		} catch (Exception e) {
//			log error
			log.error("aliyun email batch send error: ", e);
//			throw new exception
			throw new SwdaException(e.getMessage());
		}
	}

}
