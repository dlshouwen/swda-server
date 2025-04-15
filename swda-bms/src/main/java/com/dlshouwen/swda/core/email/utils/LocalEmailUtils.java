package com.dlshouwen.swda.core.email.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import com.dlshouwen.swda.bms.platform.entity.EmailPlatform;
import com.dlshouwen.swda.core.base.exception.SwdaException;

/**
 * local email utils
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Slf4j
public class LocalEmailUtils {

	/** mail account */
	private final MailAccount mailAccount;

	/**
	 * constructor
	 * @param emailPlatform
	 */
	public LocalEmailUtils(EmailPlatform emailPlatform) {
//		create mail account
		MailAccount mailAccount = new MailAccount();
//		set host
		mailAccount.setHost(emailPlatform.getMailHost());
//		if mail port is not null
		if (emailPlatform.getMailPort() != null) {
//			set port
			mailAccount.setPort(emailPlatform.getMailPort());
//			set ssl
			if (emailPlatform.getMailPort() == 465 || emailPlatform.getMailPort() == 587) {
				mailAccount.setSslEnable(true);
			}
		}
//		set from, pass
		mailAccount.setFrom(emailPlatform.getMailFrom());
		mailAccount.setPass(emailPlatform.getMailPass());
//		set mail account
		this.mailAccount = mailAccount;
	}

	/**
	 * send email
	 * @param tos
	 * @param subject
	 * @param content
	 * @param files
	 * @return message-id
	 */
	public String sendEmail(String tos, String subject, String content, boolean isHtml, File... files) {
		try {
//			send email
			return MailUtil.send(mailAccount, tos, subject, content, isHtml, files);
		} catch (Exception e) {
//			log error
			log.error("local mail send error: ", e);
//			throw exception
			throw new SwdaException(e.getMessage());
		}
	}

}
