package com.dlshouwen.swda.bms.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * file upload vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "file upload")
public class SysFileUploadVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "file name")
	private String name;

	@Schema(description = "url")
	private String url;

	@Schema(description = "size")
	private Long size;

	@Schema(description = "platform")
	private String platform;

}
