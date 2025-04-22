package com.dlshouwen.swda.bms.permission.vo;

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
	private String gender;

	@ExcelProperty(value = "性别")
	private String genderName;

	@ExcelProperty("邮箱")
	private String email;

	@ExcelProperty("手机号")
	private String mobile;

	@ExcelProperty("机构ID")
	private Long organId;

	@ExcelIgnore
	@Trans(type = TransType.DICTIONARY, key = "open_close", ref = "statusName")
	private String status;

	@ExcelProperty(value = "状态")
	private String statusName;

	@ExcelIgnore
	@Trans(type = TransType.DICTIONARY, key = "zero_one", ref = "superAdminName")
	private String superAdmin;

	@ExcelProperty(value = "超级管理员")
	private String superAdminName;

	@ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
	private LocalDateTime createTime;

}
