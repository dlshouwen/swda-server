package com.dlshouwen.swda.core.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fhs.core.trans.vo.TransPojo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * base entity
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Data
public abstract class BaseEntity implements TransPojo {

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