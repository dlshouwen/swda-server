package com.dlshouwen.swda.annotation;

import com.dlshouwen.swda.entity.base.OperationType;

import java.lang.annotation.*;

/**
 * operation
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operation {

	/** 
	 * operation type
	 */
	OperationType type() default OperationType.UNKNOWN;

	/**
	 * description
	 */
	String description() default "";

}
