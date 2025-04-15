package com.dlshouwen.swda.bms.log.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * email log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "email log")
public class EmailLogVO implements Serializable {

	/** serial version uid */
    private static final long serialVersionUID = 1L;

    @Schema(description = "email log id")
    private Long emailLogId;

    @Schema(description = "email platform id")
    private Long emailPlatformId;
    
    @Schema(description = "email platform name")
    private String emailPlatformName;

    @Schema(description = "email platform type")
    private Integer emailPlatformType;

    @Schema(description = "mail from")
    private String mailFrom;

    @Schema(description = "mail tos")
    private String mailTos;

    @Schema(description = "subject")
    private String subject;

    @Schema(description = "content")
    private String content;

    @Schema(description = "call result")
    private Integer callResult;

    @Schema(description = "error reason")
    private String errorReason;

    @Schema(description = "send time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime sendTime;

}