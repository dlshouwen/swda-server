package com.dlshouwen.swda.bms.platform.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 邮件发送
 *
 * @author 阿沐 babamu@126.com
 */
@Data
@Schema(description = "邮件发送")
public class MailSendVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long emailPlatformId;

    @Schema(description = "平台")
    private Integer emailPlatformType;

    @Schema(description = "邮件格式")
    private Integer emailFormatType;

    @Schema(description = "发件人邮箱")
    private String mailFrom;

    @Schema(description = "发件人昵称")
    private String formAlias;

    @Schema(description = "接收人邮箱")
    private String mailTos;

    @Schema(description = "收件人列表")
    private String receiversName;

    @Schema(description = "模板名")
    private String templateName;

    @Schema(description = "标签名")
    private String tagName;

    @Schema(description = "邮件主题")
    private String subject;

    @Schema(description = "邮件正文")
    private String content;

}