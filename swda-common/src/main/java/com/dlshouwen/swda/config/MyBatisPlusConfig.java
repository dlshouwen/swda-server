package com.dlshouwen.swda.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.dlshouwen.swda.injector.MySqlInjector;
import com.dlshouwen.swda.interceptor.MyBatisLogInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis plus config
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.dlshouwen.swda.*.*.mapper")
public class MyBatisPlusConfig {

	/**
	 * config mybatis plus interceptor
	 * <p>set mysql interceptor</p>
	 * @return mybatis plus interceptor
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	/**
	 * config mybatis log interceptor
	 * @return mybatis log interceptor
	 */
	@Bean
	public MyBatisLogInterceptor myInterceptor() {
		return new MyBatisLogInterceptor();
	}

	/**
	 * config mysql injector
	 * @return mysql injector
	 */
	@Bean
	public MySqlInjector sqlInjector() {
		return new MySqlInjector();
	}

}