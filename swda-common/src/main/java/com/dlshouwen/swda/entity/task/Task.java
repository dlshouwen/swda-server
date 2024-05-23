package com.dlshouwen.swda.entity.task;

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
 * task
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("bms_task")
@Schema(title="任务对象", description="任务表")
public class Task extends BaseEntity {

	@TableId(value="log_id", type=IdType.AUTO)
	@Schema(title="任务编号")
	private Long taskId;

	@NotBlank(message="任务编码不能为空")
	@Length(max=200, message="任务编码长度必须在0-200之间")
	@Schema(title="任务编码")
	private String taskCode;

	@NotBlank(message="任务名称不能为空")
	@Length(max=400, message="任务名称长度必须在0-400之间")
	@Schema(title="任务名称")
	private String taskName;

	@NotBlank(message="任务状态不能为空")
	@Length(max=2, message="任务状态长度必须在0-2之间")
	@Schema(title="任务状态")
	private String taskStatus;

	@NotBlank(message="是否永不过期不能为空")
	@Length(max=2, message="是否永不过期长度必须在0-2之间")
	@Schema(title="是否永不过期")
	private String isNeverOverdue;

	@Schema(title="过期时间")
	private Date overdueTime;

	@NotBlank(message="是否所有用户启用不能为空")
	@Length(max=2, message="是否所有用户启用长度必须在0-2之间")
	@Schema(title="是否所有用户启用")
	private String isAllUser;

	@NotNull(message="迭代时间不能为空")
	@Schema(title="迭代时间")
	private int iteratorTime;

	@NotBlank(message="触发sql不能为空")
	@Length(max=400, message="触发sql长度必须在0-400之间")
	@Schema(title="触发sql")
	private String detonateSql;

	@NotBlank(message="提示信息不能为空")
	@Length(max=400, message="提示信息长度必须在0-400之间")
	@Schema(title="提示信息")
	private String alertMessage;

	@NotNull(message="响应功能不能为空")
	@Schema(title="响应功能")
	private Long permissionId;

	@Length(max=400, message="辅助查询长度必须在0-400之间")
	@Schema(title="辅助查询")
	private String assistSearch;

	@NotNull(message="排序码不能为空")
	@Schema(title="排序码")
	private int sort;

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
