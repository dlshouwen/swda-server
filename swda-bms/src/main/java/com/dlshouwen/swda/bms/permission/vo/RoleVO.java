package com.dlshouwen.swda.bms.permission.vo;

import com.dlshouwen.swda.bms.permission.entity.Organ;
import com.dlshouwen.swda.core.base.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * role vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description = "role")
public class RoleVO implements Serializable, TransPojo {
	
	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description = "role id")
	private Long roleId;
	
	@Schema(description = "system id")
	@NotNull(message = "系统编号不能为空")
	@Trans(type = TransType.SIMPLE, target=com.dlshouwen.swda.bms.system.entity.System.class, fields = "systemName", ref="systemName")
	private Long systemId;
	
	private String systemName;
	
	@Schema(description = "organ id")
	@NotNull(message = "机构编号不能为空")
	@Trans(type = TransType.SIMPLE, target=Organ.class, fields = "organName", ref="organName")
	private Long organId;
	
	private String organName;
	
	@Schema(description = "role code")
	@NotBlank(message = "角色编码不能为空")
	private String roleCode;

	@Schema(description = "role name")
	@NotBlank(message = "角色名称不能为空")
	private String roleName;

	@Schema(description = "remark")
	private String remark;

	@Schema(description = "data scope")
	private String dataScope;

	@Schema(description = "menu id list")
	private List<Long> menuIdList;

	@Schema(description = "organ id list")
	private List<Long> organIdList;

	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;
	
	Map<String, Object> transMap = new HashMap<String, Object>();

}