package ru.otus.application.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.otus.application.Application;
import ru.otus.web.filter.AuthFilter;

@Configuration
public class ApplicationConfiguration extends AnnotationConfigWebApplicationContext {
	@Bean(name = "loggerApplication")
	Logger loggerApplication() {
		return LoggerFactory.getLogger(Application.class);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> authFilter() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AuthFilter());
		registrationBean.addUrlPatterns("/");
		registrationBean.addUrlPatterns("/list");
		registrationBean.addUrlPatterns("/create");
		return registrationBean;
	}
}
