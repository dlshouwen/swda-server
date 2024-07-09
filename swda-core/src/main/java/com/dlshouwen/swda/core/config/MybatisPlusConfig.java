package com.dlshouwen.swda.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.dlshouwen.swda.core.handler.FieldMetaObjectHandler;
import com.dlshouwen.swda.core.interceptor.DataScopeInnerInterceptor;

/**
 * mybatis plus config
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@Configuration
public class MybatisPlusConfig {
	
	/**
	 * mybatis plus interceptor
	 * @return
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
//		defined interceptor
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
//		set data scope interceptor
		mybatisPlusInterceptor.addInnerInterceptor(new DataScopeInnerInterceptor());
//		set pagination interceptor
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//		set optimistic locker interceptor
		mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
//		set block attack interceptor
		mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
//		return interceptor
		return mybatisPlusInterceptor;
	}

	/**
	 * field meta object handler
	 * @return
	 */
	@Bean
	public FieldMetaObjectHandler fieldMetaObjectHandler(){
		return new FieldMetaObjectHandler();
	}

}
