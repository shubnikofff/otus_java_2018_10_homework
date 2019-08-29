package ru.otus.application.configuration;

import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AnnotationsBasedApplicationInitializer extends AbstractContextLoaderInitializer {
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(ApplicationConfiguration.class);
		return context;
	}
}
