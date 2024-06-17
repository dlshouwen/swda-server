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
 * <p>操作日志</p>
 * @author 大连首闻科技有限公司
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_operation_log")
@Schema(description="操作日志表")
public class OperationLog extends BaseEntity {

    @TableId(value = "log_id", type = IdType.AUTO)
    @Schema(description = "日志编号")
    private Long logId;

    @NotBlank(message="调用来源不能为空")
    @Length(max=200, message="调用来源长度必须在0-200之间")
    @Schema(description = "调用来源")
    private String callSource;
    
    private String url;
    
    private String method;
    
    private String userAgent;

    @NotBlank(message="操作地址不能为空")
    @Schema(description = "操作地址")
    private String module;

    @NotBlank(message="操作说明不能为空")
    @Schema(description = "操作说明")
    private String name;

    @Schema(description = "参数")
    private String params;

    @NotBlank(message="操作类型不能为空")
    @Schema(description = "操作类型")
    private int operationType;

    @NotBlank(message="操作结果不能为空")
    @Length(max=20, message="操作结果长度必须在0-20之间")
    @Schema(description = "操作结果")
    private String operationResult;
    
    private int callResult;

    @Schema(description = "错误原因")
    private String errorReason;

    @NotNull(message="响应开始时间不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "响应开始时间")
    private LocalDateTime responseStart;

    @NotNull(message="响应结束时间不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Schema(description = "响应结束时间")
    private LocalDateTime responseEnd;

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
