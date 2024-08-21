package com.dlshouwen.swda.core.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dlshouwen.swda.core.log.enums.OperateType;

import cn.hutool.core.annotation.AliasFor;

/**
 * operation
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@io.swagger.v3.oas.annotations.Operation
public @interface Operation {

	/** module */
	String module() default "";

	/** name */
	@AliasFor(annotation = io.swagger.v3.oas.annotations.Operation.class, attribute = "summary")
	String name() default "";

	/** type */
	OperateType[] type() default OperateType.OTHER;
}
