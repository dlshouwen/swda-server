package com.dlshouwen.swda.bms.system.vo;

import com.dlshouwen.swda.bms.system.entity.Organ;
import com.dlshouwen.swda.core.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * user vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "user")
public class UserVO implements Serializable, TransPojo {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "user id")
	private Long userId;

	@Schema(description = "organ id")
	@NotNull(message = "机构ID不能为空")
	@Trans(type = TransType.SIMPLE, target = Organ.class, fields = "name", ref = "orgName")
	private Long organId;

	@Schema(description = "user code")
	@NotBlank(message = "用户名不能为空")
	private String userCode;

	@Schema(description = "user name")
	@NotBlank(message = "用户名不能为空")
	private String userName;

	@Schema(description = "real name")
	@NotBlank(message = "姓名不能为空")
	private String realName;

	@Schema(description = "avatar")
	private String avatar;

	@Schema(description = "password")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Schema(description = "password expire time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime passwordExpireTime;

	@Schema(description = "super admin")
	@Range(min = 0, max = 1, message = "超级管理员不正确")
	private Integer superAdmin;
	
	@Schema(description = "status")
	@Range(min = 0, max = 1, message = "状态不正确")
	private Integer status;

	@Schema(description = "gender")
	@Range(min = 0, max = 2, message = "性别不正确")
	private Integer gender;

	@Schema(description = "card id")
	private String cardId;

	@Schema(description = "mobile")
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@Schema(description = "email")
	@Email(message = "邮箱格式不正确")
	private String email;

	@Schema(description = "address")
	private String address;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

	@Schema(description = "role id list")
	private List<Long> roleIdList;

	@Schema(description = "post id list")
	private List<Long> postIdList;

	@Schema(description = "post name list")
	private List<String> postNameList;

	@Schema(description = "organ name")
	private String organName;

}
