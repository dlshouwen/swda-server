package com.dlshouwen.swda.entity.assist;

import com.baomidou.mybatisplus.annotation.*;
import com.dlshouwen.swda.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * user
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_user")
@Schema(title="用户对象", description="用户表")
public class User extends BaseEntity {

	@TableId(value="log_id", type=IdType.AUTO)
	@Schema(title="用户编号")
	private Long userId;

	@NotNull(message="机构编号不能为空")
	@Schema(title="机构编号")
	private Long organId;

	@NotBlank(message="用户编码不能为空")
	@Length(max=200, message="用户编码长度必须在0-200之间")
	@Schema(title="用户编码")
	private String userCode;

	@NotBlank(message="用户名称不能为空")
	@Length(max=400, message="用户名称长度必须在0-400之间")
	@Schema(title="用户名称")
	private String userName;

	@NotBlank(message="密码不能为空")
	@Length(max=80, message="密码长度必须在0-80之间")
	@Schema(title="密码")
	private String password;

	@NotNull(message="密码过期时间不能为空")
	@Schema(title="密码过期时间")
	private Date passwordExpireTime;

	@NotBlank(message="是否启用不能为空")
	@Length(max=2, message="是否启用长度必须在0-2之间")
	@Schema(title="是否启用")
	private String isEnable;

	@NotBlank(message="性别不能为空")
	@Length(max=2, message="性别长度必须在0-2之间")
	@Schema(title="性别")
	private String sex;

	@Length(max=80, message="证件号长度必须在0-80之间")
	@Schema(title="证件号")
	private String cardId;

	@Length(max=20, message="联系电话长度必须在0-20之间")
	@Schema(title="联系电话")
	private String phone;

	@Length(max=200, message="电子邮箱长度必须在0-200之间")
	@Schema(title="电子邮箱")
	private String email;

	@Length(max=600, message="联系地址长度必须在0-600之间")
	@Schema(title="联系地址")
	private String address;

	@Length(max=400, message="辅助查询长度必须在0-400之间")
	@Schema(title="辅助查询")
	private String assistSearch;

	@Length(max=200, message="备注长度必须在0-200之间")
	@Schema(title="备注")
	private String remark;

	@Schema(title="创建人")
	private Long creator;

	@TableField(fill=FieldFill.INSERT)
	@Schema(title="创建时间")
	private Date createTime;

	@Schema(title="编辑人")
	private Long editor;

	@TableField(fill=FieldFill.INSERT_UPDATE)
	@Schema(title="编辑时间")
	private Date editTime;

	@Length(max=2, message="是否删除长度必须在0-2之间")
	@Schema(title="是否删除")
	private String isDelete;

	@Schema(title="删除人")
	private Long deleter;

	@Schema(title="删除时间")
	private Date deleteTime;

}
