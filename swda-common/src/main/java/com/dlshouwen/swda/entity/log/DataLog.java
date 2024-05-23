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
 * data log
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_data_log")
@Schema(title="数据日志对象", description="数据日志表")
public class DataLog extends BaseEntity {

	@TableId(value="log_id", type=IdType.AUTO)
	@Schema(title="日志编号")
	private Long logId;

	@NotBlank(message="调用方式不能为空")
	@Length(max=2, message="调用方式长度必须在0-2之间")
	@Schema(title="调用方式")
	private String callType;

	@NotBlank(message="调用来源不能为空")
	@Length(max=200, message="调用来源长度必须在0-200之间")
	@Schema(title="调用来源")
	private String callSource;

	@NotNull(message="行号不能为空")
	@Schema(title="行号")
	private int lineNo;

	@NotBlank(message="操作类型不能为空")
	@Length(max=2, message="操作类型长度必须在0-2之间")
	@Schema(title="操作类型")
	private String operationType;

	@NotBlank(message="操作sql不能为空")
	@Schema(title="操作sql")
	private String operationSql;

	@Schema(title="参数")
	private String params;

	@NotBlank(message="执行结果不能为空")
	@Length(max=2, message="执行结果长度必须在0-2之间")
	@Schema(title="执行结果")
	private String callResult;

	@Schema(title="错误原因")
	private String errorReason;

	@NotBlank(message="执行类别不能为空")
	@Length(max=20, message="执行类别长度必须在0-20之间")
	@Schema(title="执行类别")
	private String executeType;

	@Schema(title="执行结果")
	private String executeResult;

	@NotBlank(message="结果类别不能为空")
	@Length(max=200, message="结果类别长度必须在0-200之间")
	@Schema(title="结果类别")
	private String resultType;

	@NotNull(message="执行开始时间不能为空")
	@Schema(title="执行开始时间")
	private Date startTime;

	@NotNull(message="执行结束时间不能为空")
	@Schema(title="执行结束时间")
	private Date endTime;

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
