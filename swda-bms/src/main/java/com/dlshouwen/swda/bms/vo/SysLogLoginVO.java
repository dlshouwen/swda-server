package com.dlshouwen.swda.bms.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.dlshouwen.swda.core.excel.LocalDateTimeConverter;
import com.dlshouwen.swda.core.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * login log vo
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
@Schema(description = "login log")
public class SysLogLoginVO implements Serializable, TransPojo {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	@Schema(description = "id")
	private Long id;

	@ExcelProperty("用户名")
	@Schema(description = "username")
	private String username;

	@ExcelProperty("登录IP")
	@Schema(description = "ip")
	private String ip;

	@ExcelProperty("登录地点")
	@Schema(description = "address")
	private String address;

	@ExcelProperty("User Agent")
	@Schema(description = "user agent")
	private String userAgent;

	@ExcelIgnore
	@Trans(type = TransType.DICTIONARY, key = "success_fail", ref = "statusLabel")
	@Schema(description = "status")
	private Integer status;

	@ExcelProperty(value = "status label")
	private String statusLabel;

	@ExcelIgnore
	@Trans(type = TransType.DICTIONARY, key = "login_operation", ref = "operationLabel")
	@Schema(description = "operation")
	private Integer operation;

	@ExcelProperty(value = "operation label")
	private String operationLabel;

	@ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
	@Schema(description = "create time")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private LocalDateTime createTime;

}
