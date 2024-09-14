package com.dlshouwen.swda.bms.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * user avatar vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "user avatar")
public class UserAvatarVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "头像不能为空")
	@Schema(description = "avatar")
	private String avatar;

}
