package com.site.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	//配置controller
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/setpwd").setViewName("pwd");
		registry.addViewController("/signDetail").setViewName("signDetail");
	}
}
