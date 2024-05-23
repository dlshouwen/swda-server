package com.dlshouwen.swda.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * login log
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_login_log")
@Schema(title="登录日志对象", description="登录日志表")
public class LoginLog extends BaseEntity {

	@TableId(value="log_id", type=IdType.AUTO)
	@Schema(title="日志内码")
	private Long logId;

	@NotBlank(message="token不能为空")
	@Length(max=400, message="token长度必须在0-400之间")
	@Schema(title="token")
	private String token;

	@NotNull(message="用户编号不能为空")
	@Schema(title="用户编号")
	private Long userId;

	@NotBlank(message="用户名称不能为空")
	@Length(max=400, message="用户名称长度必须在0-400之间")
	@Schema(title="用户名称")
	private String userName;

	@NotNull(message="机构编号不能为空")
	@Schema(title="机构编号")
	private Long organId;

	@NotBlank(message="机构名称不能为空")
	@Length(max=400, message="机构名称长度必须在0-400之间")
	@Schema(title="机构名称")
	private String organName;

	@NotNull(message="登录时间不能为空")
	@Schema(title="登录时间")
	private Date loginTime;

	@NotBlank(message="登录ip不能为空")
	@Length(max=40, message="登录ip长度必须在0-40之间")
	@Schema(title="登录ip")
	private String ip;

	@NotBlank(message="登录状态不能为空")
	@Length(max=2, message="登录状态长度必须在0-2之间")
	@Schema(title="登录状态")
	private String loginStatus;

	@NotBlank(message="是否登出不能为空")
	@Length(max=2, message="是否登出长度必须在0-2之间")
	@Schema(title="是否登出")
	private String isLogout;

	@Length(max=2, message="登出状态长度必须在0-2之间")
	@Schema(title="登出状态")
	private String logoutType;

	@Schema(title="登出时间")
	private Date logoutTime;

}
