package com.dlshouwen.swda.bms.tool.vo;

import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * attachment vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "attachment")
public class AttachmentVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "attachment id")
	private Long attachmentId;

	@Schema(description = "file name")
	private String fileName;

	@Schema(description = "file url")
	private String fileUrl;

	@Schema(description = "file size")
	private Long fileSize;

	@Schema(description = "storage platform")
	private String storagePlatform;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}