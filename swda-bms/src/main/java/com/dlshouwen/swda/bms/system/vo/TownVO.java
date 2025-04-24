package com.dlshouwen.swda.bms.system.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.dlshouwen.swda.bms.system.entity.Region;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;

/**
 * town vo
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@Schema(description="town")
public class TownVO implements Serializable, TransPojo {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@Schema(description="town id")
	private Integer townId;

	@Schema(description="province id")
	@NotNull(message="所属省不能为空")
	@Trans(type=TransType.SIMPLE, target=Region.class, fields="regionName", ref="provinceName")
	private Integer provinceId;
	
	private String provinceName;

	@Schema(description="city id")
	@NotNull(message="所属市不能为空")
	@Trans(type=TransType.SIMPLE, target=Region.class, fields="regionName", ref="cityName")
	private Integer cityId;
	
	private String cityName;

	@Schema(description="county id")
	@NotNull(message="所属区不能为空")
	@Trans(type=TransType.SIMPLE, target=Region.class, fields="regionName", ref="countyName")
	private Integer countyId;
	
	private String countyName;

	@Schema(description="town name")
	@NotBlank(message="街道名称不能为空")
	@Length(min=0, max=200, message="街道名称长度必须在0-200之间")
	private String townName;

	@Schema(description="town full name")
	@NotBlank(message="街道全名不能为空")
	@Length(min=0, max=400, message="街道全名长度必须在0-400之间")
	private String townFullName;

	@Schema(description="town name pinyin")
	@NotBlank(message="街道名称拼音不能为空")
	@Length(min=0, max=200, message="街道名称拼音长度必须在0-200之间")
	private String townNamePinyin;

	@Schema(description="town name full pinyin")
	@NotBlank(message="街道名称全拼不能为空")
	@Length(min=0, max=400, message="街道名称全拼长度必须在0-400之间")
	private String townNameFullPinyin;

	@Schema(description="postal code")
	@NotBlank(message="邮政编码不能为空")
	@Length(min=0, max=6, message="邮政编码长度必须在0-6之间")
	private String postalCode;

	@Schema(description="sort")
	@NotNull(message="排序码不能为空")
	private Integer sort;

	@Schema(description="remark")
	@Length(min=0, max=200, message="备注长度必须在0-200之间")
	private String remark;

	@Schema(description="create time")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	
	Map<String, Object> transMap = new HashMap<String, Object>();

}
