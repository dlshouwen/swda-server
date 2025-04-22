package com.dlshouwen.swda.bms.platform.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * email send
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "email send")
public class EmailSendVO implements Serializable {
	
	/** serial version uid */
    private static final long serialVersionUID = 1L;

    @Schema(description = "email platform id")
    private Long emailPlatformId;

    @Schema(description = "email platform type")
    private String emailPlatformType;

    @Schema(description = "email format type")
    private String emailFormatType;

    @Schema(description = "mail from")
    private String mailFrom;

    @Schema(description = "from alias")
    private String formAlias;

    @Schema(description = "mail tos")
    private String mailTos;

    @Schema(description = "receivers name")
    private String receiversName;

    @Schema(description = "template name")
    private String templateName;

    @Schema(description = "tag name")
    private String tagName;

    @Schema(description = "subject")
    private String subject;

    @Schema(description = "content")
    private String content;

}