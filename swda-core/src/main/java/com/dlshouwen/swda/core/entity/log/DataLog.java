package com.dlshouwen.swda.core.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dlshouwen.swda.core.entity.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>数据日志</p>
 * @author 大连首闻科技有限公司
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_data_log")
@Schema(description="数据日志表")
public class DataLog extends BaseEntity {

    @TableId(value = "log_id", type = IdType.AUTO)
    @Schema(description = "日志编号")
    private Long logId;

    @NotBlank(message="调用方式不能为空")
    @Length(max=2, message="调用方式长度必须在0-2之间")
    @Schema(description = "调用方式")
    private Integer callType;

    @NotBlank(message="调用来源不能为空")
    @Length(max=200, message="调用来源长度必须在0-200之间")
    @Schema(description = "调用来源")
    private String callSource;

    @NotNull(message="行号不能为空")
    @Schema(description = "行号")
    private int lineNo;

    @NotBlank(message="操作类型不能为空")
    @Length(max=2, message="操作类型长度必须在0-2之间")
    @Schema(description = "操作类型")
    private Integer operationType;

    @NotBlank(message="操作sql不能为空")
    @Schema(description = "操作sql")
    private String operationSql;

    @Schema(description = "参数")
    private String params;

    @NotBlank(message="执行结果不能为空")
    @Length(max=2, message="执行结果长度必须在0-2之间")
    @Schema(description = "执行结果")
    private Integer callResult;

    @Schema(description = "错误原因")
    private String errorReason;

    @NotBlank(message="执行类别不能为空")
    @Length(max=20, message="执行类别长度必须在0-20之间")
    @Schema(description = "执行类别")
    private String executeType;

    @Schema(description = "执行结果")
    private String executeResult;

    @NotBlank(message="结果类别不能为空")
    @Length(max=200, message="结果类别长度必须在0-200之间")
    @Schema(description = "结果类别")
    private String resultType;

    @NotNull(message="执行开始时间不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "执行开始时间")
    private LocalDateTime startTime;

    @NotNull(message="执行结束时间不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "执行结束时间")
    private LocalDateTime endTime;

    @NotNull(message="耗时不能为空")
    @Schema(description = "耗时")
    private int cost;

    @Schema(description = "用户编号")
    private Long userId;

    @Length(max=400, message="用户名称长度必须在0-400之间")
    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "机构编号")
    private Long organId;

    @Length(max=400, message="机构名称长度必须在0-400之间")
    @Schema(description = "机构名称")
    private String organName;

    @NotBlank(message="ip地址不能为空")
    @Length(max=40, message="ip地址长度必须在0-40之间")
    @Schema(description = "ip地址")
    private String ip;

}
