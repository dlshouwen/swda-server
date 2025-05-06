package com.dlshouwen.swda.core.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fhs.core.trans.vo.TransPojo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * base entity
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Data
public abstract class BaseEntity implements TransPojo, Serializable {

	/** serial version uid */
	private static final long serialVersionUID = -5634620377160411649L;
	
	/**
	 * info
	 */
	@TableField(exist = false)
	private Map<String, Object> info = new HashMap<>();

	/**
	 * set info
	 * @param key
	 * @param value
	 */
	public void setInfo(String key, Object value) {
		info.put(key, value);
	}

}