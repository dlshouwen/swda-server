package com.dlshouwen.swda.bms.oss.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * file upload vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "file upload")
public class FileUploadVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "file name")
	private String fileName;

	@Schema(description = "file url")
	private String fileUrl;

	@Schema(description = "file size")
	private Long fileSize;

	@Schema(description = "storage platform")
	private Integer storagePlatform;

}
