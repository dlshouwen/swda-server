package com.dlshouwen.swda.bms.system.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.dlshouwen.swda.core.excel.converter.LocalDateTimeConverter;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * user excel
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public class UserExcelVO implements Serializable, TransPojo {
	
	/** servial version uid */
	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	private Long userId;
	
	@ExcelProperty("用户编码")
	private String userCode;

	@ExcelProperty("用户名")
	private String userName;

	@ExcelProperty("真实姓名")
	private String realName;

	@ExcelIgnore
	@Trans(type = TransType.DICTIONARY, key = "gender", ref = "genderName")
	private Integer gender;

	@ExcelProperty(value = "性别")
	private String genderName;

	@ExcelProperty("邮箱")
	private String email;

	@ExcelProperty("手机号")
	private String mobile;

	@ExcelProperty("机构ID")
	private Long organId;

	@ExcelIgnore
	@Trans(type = TransType.DICTIONARY, key = "user_status", ref = "statusLabel")
	private Integer status;

	@ExcelProperty(value = "状态")
	private String statusLabel;

	@ExcelIgnore
	@Trans(type = TransType.DICTIONARY, key = "user_super_admin", ref = "superAdminLabel")
	private Integer superAdmin;

	@ExcelProperty(value = "超级管理员")
	private String superAdminLabel;

	@ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
	private LocalDateTime createTime;

}
