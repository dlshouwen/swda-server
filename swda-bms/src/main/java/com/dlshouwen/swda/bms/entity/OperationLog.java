package com.dlshouwen.swda.bms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * operation log
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */

@Data
@TableName("sys_log_operate")
public class OperationLog {

	@TableId
	private Long id;

	private Long userId;

	private String realName;

	private String module;

	private String name;

	private String reqUri;

	private String reqMethod;

	private String reqParams;

	private String ip;

	private String address;

	private String userAgent;

	private Integer operateType;

	private Integer duration;

	private Integer status;

	private String resultMsg;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	private Long tenantId;

}