package com.dlshouwen.swda.core.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * swagger config
 * @author liujingcheng@live.cn
 * @version 1.0.0
 */
@Configuration
public class SwaggerConfig {
	
	/**
	 * user api
	 * @return
	 */
	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi.builder().group("SWDA").pathsToMatch(new String[] {"/**"}).packagesToScan(new String[] {"com.dlshouwen.swda"}).build();
	}

	/**
	 * custom open api
	 * @return
	 */
	@Bean
	public OpenAPI customOpenAPI() {
//		set contact
		Contact contact = new Contact();
		contact.setName(" service@dlshouwen.com");
//		set open api info
		return new OpenAPI().info(new Info()
				.title("SWDA")
				.description("DaLian ShouWen Detach Architecture")
				.contact(contact)
				.version("1.0.0")
				.termsOfService("https://swda.dlshouwen.com")
				.license(new License().name("MIT").url("https://swda.dlshouwen.com")));
	}

}
