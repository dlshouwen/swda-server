package com.dlshouwen.swda.bms.query;

import com.dlshouwen.swda.core.query.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * attachement query
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "attachment query")
public class SysAttachmentQuery extends Query {

	@Schema(description = "name")
	private String name;

	@Schema(description = "platform")
	private String platform;

}