package com.dlshouwen.swda.bms.log.vo;

import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sms log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "sms log")
public class SmsLogVO implements Serializable {

	/** serial version uid */
    private static final long serialVersionUID = 1L;

    @Schema(description = "sms log id")
    private Long smsLogId;

    @Schema(description = "sms platform id")
    private Long smsPlatformId;
    
    @Schema(description = "sms platform name")
    private String smsPlatformName;

    @Schema(description = "sms platform type")
    private Integer smsPlatformType;

    @Schema(description = "mobile")
    private String mobile;
    
    @Schema(description = "params")
    private String params;

    @Schema(description = "call result")
    private Integer callResult;

    @Schema(description = "error reason")
    private String error;

    @Schema(description = "send time")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime sendTime;

}