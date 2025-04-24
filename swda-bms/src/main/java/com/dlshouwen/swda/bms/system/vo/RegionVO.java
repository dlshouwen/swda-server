package com.dlshouwen.swda.bms.system.vo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.dlshouwen.swda.bms.system.entity.Region;
import com.dlshouwen.swda.core.base.entity.TreeNode;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;

/**
 * region vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description="region")
public class RegionVO extends TreeNode<RegionVO> implements TransPojo {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description="region id")
	private Integer regionId;

	@Schema(description="pre region id")
	@NotNull(message="上级区域编号不能为空")
	@Trans(type=TransType.SIMPLE, target=Region.class, fields="regionFullName", ref="preRegionFullName")
	private Integer preRegionId;
	
	private String preRegionFullName;

	@Schema(description="region type")
	@NotBlank(message="区域类型不能为空")
	@Length(min=0, max=2, message="区域类型长度必须在0-2之间")
	private String regionType;

	@Schema(description="region name")
	@NotBlank(message="区域名称不能为空")
	@Length(min=0, max=200, message="区域名称长度必须在0-200之间")
	private String regionName;

	@Schema(description="region full name")
	@NotBlank(message="区域全名不能为空")
	@Length(min=0, max=400, message="区域全名长度必须在0-400之间")
	private String regionFullName;

	@Schema(description="region name pinyin")
	@NotBlank(message="区域名称拼音不能为空")
	@Length(min=0, max=200, message="区域名称拼音长度必须在0-200之间")
	private String regionNamePinyin;

	@Schema(description="region name full pinyin")
	@NotBlank(message="区域名称全拼不能为空")
	@Length(min=0, max=400, message="区域名称全拼长度必须在0-400之间")
	private String regionNameFullPinyin;

	@Schema(description="postal code")
	@NotBlank(message="邮政编码不能为空")
	@Length(min=0, max=6, message="邮政编码长度必须在0-6之间")
	private String postalCode;

	@Schema(description="is open")
	@NotBlank(message="是否启用不能为空")
	@Length(min=0, max=2, message="是否启用长度必须在0-2之间")
	private String isOpen;

	@Schema(description="is hot")
	@NotBlank(message="是否热门不能为空")
	@Length(min=0, max=2, message="是否热门长度必须在0-2之间")
	private String isHot;

	@Schema(description="sort")
	@NotNull(message="排序码不能为空")
	private Integer sort;

	@Schema(description="remark")
	@Length(min=0, max=200, message="备注长度必须在0-200之间")
	private String remark;

	@Schema(description="create time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	
	@Schema(description="has children")
	private Boolean hasChildren;
	
	Map<String, Object> transMap = new HashMap<String, Object>();

}
