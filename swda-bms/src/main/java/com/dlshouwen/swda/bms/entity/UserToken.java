package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * user token
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */

@Data
@TableName("sys_user_token")
public class UserToken {

	@TableId
	private Long id;

	private Long userId;

	private String accessToken;

	private LocalDateTime accessTokenExpire;

	private String refreshToken;

	private LocalDateTime refreshTokenExpire;

	private Long tenantId;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

}