package com.dlshouwen.swda.config;

import com.dlshouwen.swda.entity.base.Data;
import com.dlshouwen.swda.loader.DataLoader;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.*;

/**
 * data config
 * @author liujingcheng@live.cn
 * @since 0.0.1-SNAPSHOT
 */
@Configuration
public class DataConfig {

	/**
	 * jdbc template
	 */
	@Autowired
	JdbcTemplate template;

	/**
	 * post construct
	 * <p>init to load data</p>
	 */
	@PostConstruct
	public void postConstruct(){
//		load
		DataLoader.load(template);
//		unique
		try {
			Properties unique = PropertiesLoaderUtils.loadAllProperties("unique.properties");
			for(Object key : unique.keySet()) {
				Data.unique.put((String)key, (String)unique.get(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
