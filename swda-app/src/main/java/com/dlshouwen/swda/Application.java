package com.dlshouwen.swda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * application
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.dlshouwen.swda.*.*.mapper")
@EnableCaching
public class Application extends SpringBootServletInitializer {

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * configure
	 * @param application
	 * @return spring application builder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

}
