package com.dlshouwen.swda.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * base entity
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Data
public class BaseEntity {

	/** 
	 * info
	 * <p>query for map not has filed set to this info</p>
	 */
	@TableField(exist=false)
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
