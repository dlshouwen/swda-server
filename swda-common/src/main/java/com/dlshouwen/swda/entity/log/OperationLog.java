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
 * operation log
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_operation_log")
@Schema(title="操作日志对象", description="操作日志表")
public class OperationLog extends BaseEntity {

	@TableId(value="log_id", type=IdType.AUTO)
	@Schema(title="日志编号")
	private Long logId;

	@NotBlank(message="调用来源不能为空")
	@Length(max=200, message="调用来源长度必须在0-200之间")
	@Schema(title="调用来源")
	private String callSource;

	@NotBlank(message="操作地址不能为空")
	@Schema(title="操作地址")
	private String operationUrl;

	@Schema(title="参数")
	private String params;

	@NotBlank(message="操作类型不能为空")
	@Length(max=2, message="操作类型长度必须在0-2之间")
	@Schema(title="操作类型")
	private String operationType;

	@NotBlank(message="操作结果不能为空")
	@Length(max=20, message="操作结果长度必须在0-20之间")
	@Schema(title="操作结果")
	private String operationResult;

	@Schema(title="错误原因")
	private String errorReason;

	@NotBlank(message="操作说明不能为空")
	@Schema(title="操作说明")
	private String operationDetail;

	@NotNull(message="响应开始时间不能为空")
	@Schema(title="响应开始时间")
	private Date responseStart;

	@NotNull(message="响应结束时间不能为空")
	@Schema(title="响应结束时间")
	private Date responseEnd;

	@NotNull(message="耗时不能为空")
	@Schema(title="耗时")
	private int cost;

	@Schema(title="用户编号")
	private Long userId;

	@Length(max=400, message="用户名称长度必须在0-400之间")
	@Schema(title="用户名称")
	private String userName;

	@Schema(title="机构编号")
	private Long organId;

	@Length(max=400, message="机构名称长度必须在0-400之间")
	@Schema(title="机构名称")
	private String organName;

	@NotBlank(message="ip地址不能为空")
	@Length(max=40, message="ip地址长度必须在0-40之间")
	@Schema(title="ip地址")
	private String ip;

}
