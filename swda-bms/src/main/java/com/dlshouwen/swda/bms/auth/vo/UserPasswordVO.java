package com.dlshouwen.swda.bms.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * user password vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "user password")
public class UserPasswordVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "password")
	@NotBlank(message = "原密码不能为空")
	private String password;

	@Schema(description = "new password")
	@Length(min = 4, max = 20, message = "新密码长度为 4-20 位")
	private String newPassword;

}