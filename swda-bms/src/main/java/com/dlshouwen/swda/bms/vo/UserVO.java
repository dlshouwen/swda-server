package com.dlshouwen.swda.bms.vo;

import com.dlshouwen.swda.bms.entity.SysOrgEntity;
import com.dlshouwen.swda.core.utils.DateUtils;
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

	@Schema(description = "id")
	private Long id;

	@Schema(description = "username")
	@NotBlank(message = "用户名不能为空")
	private String username;

	@Schema(description = "password")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

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

	@Schema(description = "mobile")
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@Schema(description = "organ id")
	@NotNull(message = "机构ID不能为空")
	@Trans(type = TransType.SIMPLE, target = SysOrgEntity.class, fields = "name", ref = "orgName")
	private Long orgId;

	@Schema(description = "status")
	@Range(min = 0, max = 1, message = "用户状态不正确")
	private Integer status;

	@Schema(description = "role id list")
	private List<Long> roleIdList;

	@Schema(description = "post id list")
	private List<Long> postIdList;

	@Schema(description = "post name list")
	private List<String> postNameList;

	@Schema(description = "super admin")
	private Integer superAdmin;

	@Schema(description = "organ name")
	private String orgName;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}
