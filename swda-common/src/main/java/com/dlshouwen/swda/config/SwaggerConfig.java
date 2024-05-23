package com.dlshouwen.swda.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * swagger config
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
public class SwaggerConfig {
	
	@Bean
    public GroupedOpenApi SwdaApi() {
        return GroupedOpenApi.builder().group("swda").pathsToMatch("/admin/**").build();
    }

    @Bean
    public OpenAPI docsOpenApi()
    {
        return new OpenAPI().info(new Info().title("swda-server").description("DaLian Shouwen Detach Architecture").version("1.0.0"))
        		.externalDocs(new ExternalDocumentation().description("www.dlshouwen.com").url("https://www.dlshouwen.com"));
    }
	
}