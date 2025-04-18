package com.dlshouwen.swda.core.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * user token
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */

@Data
@TableName("bms_user_token")
public class UserToken {

	@TableId
	private Long relationId;

	private Long userId;

	private String accessToken;

	private LocalDateTime accessTokenExpire;

	private String refreshToken;

	private LocalDateTime refreshTokenExpire;

	private Long tenantId;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

}