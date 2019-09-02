package ru.otus.application.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.application.Application;
import ru.otus.application.service.DBService;
import ru.otus.dao.Dao;
import ru.otus.dao.HibernateDaoFactory;
import ru.otus.model.Address;
import ru.otus.model.Phone;
import ru.otus.model.User;

@Configuration
public class ApplicationConfiguration {
	@Value("${hibernate.config.path:hibernate.cfg.xml}")
	private String hibernateConfigurationPath;

	@Bean(name = "loggerApplication")
	Logger loggerApplication() {
		return LoggerFactory.getLogger(Application.class);
	}

	@Bean(name = "loggerDBService")
	Logger loggerProcessStarter() {
		return LoggerFactory.getLogger(DBService.class);
	}

	@Bean
	Dao dao() {
		return new HibernateDaoFactory(hibernateConfigurationPath).getDao(User.class, Address.class, Phone.class);
	}
}
