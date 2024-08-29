package com.dlshouwen.swda.bms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * bms application
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.dlshouwen"})
public class BmsApplication {

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
//		spring application run
		SpringApplication.run(BmsApplication.class, args);
	}

}