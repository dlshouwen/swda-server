package com.dlshouwen.swda.bms.permission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * user assist vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "user assist")
public class LoginUserVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	@Schema(description = "username")
	@NotBlank(message = "用户名不能为空")
	private String username;

	@Schema(description = "real name")
	@NotBlank(message = "真实姓名不能为空")
	private String realName;

	@Schema(description = "avatar")
	private String avatar;

	@Schema(description = "gender")
	@Range(min = 0, max = 2, message = "性别不正确")
	private String gender;

	@Schema(description = "card id")
	private String cardId;

	@Schema(description = "email")
	@Email(message = "邮箱格式不正确")
	private String email;

	@Schema(description = "mobile")
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@Schema(description = "address")
	private String address;

}
