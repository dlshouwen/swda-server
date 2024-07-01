package com.dlshouwen.swda.bms.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * user base vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "user base")
public class UserAssistVO implements Serializable {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "real name")
	@NotBlank(message = "姓名不能为空")
	private String realName;

	@Schema(description = "avatar")
	private String avatar;

	@Schema(description = "gender")
	@Range(min = 0, max = 2, message = "性别不正确")
	private Integer gender;

	@Schema(description = "email")
	@Email(message = "邮箱格式不正确")
	private String email;

	@Schema(description = "mobile", required = true)
	@NotBlank(message = "手机号不能为空")
	private String mobile;

}
