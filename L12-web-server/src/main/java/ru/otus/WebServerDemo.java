package ru.otus;

import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;
import ru.otus.dao.HibernateConfigurationBuilder;
import ru.otus.model.Address;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.webServer.ServerBuilder;

public class WebServerDemo {
	private Server server;

	private WebServerDemo() {
		SessionFactory sessionFactory = HibernateConfigurationBuilder
				.builder()
				.setXmlConfig("hibernate.cfg.xml")
				.addAnnotatedClasses(User.class, Address.class, Phone.class)
				.getSessionFactory();

		server = new ServerBuilder()
				.withResourceHandler("/static")
				.withContextHandler(sessionFactory)
				.build(8080);

	}

	private void startWebServer() throws Exception {
		server.start();
		server.join();
	}

	public static void main(String[] args) throws Exception {
		final WebServerDemo webServerDemo = new WebServerDemo();

		webServerDemo.startWebServer();
	}
}
