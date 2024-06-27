package com.dlshouwen.swda.bms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * bms application
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
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