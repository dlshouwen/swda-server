package com.dlshouwen.swda.bms.log.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * email log
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
@TableName("bms_email_log")
public class EmailLog {

	@TableId
    private Long emailLogId;

    private Long emailPlatformId;

    private String emailPlatformName;
    
    private Integer emailPlatformType;

    private String mailFrom;

    private String mailTos;

    private String subject;

    private String content;

    private Integer callResult;

    private String errorReason;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime sendTime;
   
}