package ru.otus.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SpringContextListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringConfig.xml");
		sce.getServletContext().setAttribute("applicationContext", applicationContext);
	}
}
