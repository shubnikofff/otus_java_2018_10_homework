package ru.otus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.SessionFactory;
import ru.otus.dao.HibernateConfigurationBuilder;
import ru.otus.filter.AuthFilter;
import ru.otus.model.Address;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.server.ServerBuilder;
import ru.otus.servlet.CreateUserServlet;
import ru.otus.servlet.AdminServlet;
import ru.otus.servlet.LoginServlet;
import ru.otus.servlet.ListUserServlet;

public class WebServerDemo {
	public static void main(String[] args) throws Exception {
		final WebServerDemo webServerDemo = new WebServerDemo();

		webServerDemo.startWebServer();
	}

	private SessionFactory sessionFactory;

	private WebServerDemo() {
		sessionFactory = HibernateConfigurationBuilder
				.builder()
				.setXmlConfig("hibernate.cfg.xml")
				.addAnnotatedClasses(User.class, Address.class, Phone.class)
				.getSessionFactory();
	}

	private void startWebServer() throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(new ServletHolder(new AdminServlet()), "/admin");
		context.addServlet(new ServletHolder(new LoginServlet()), "/login");
		context.addServlet(new ServletHolder(new ListUserServlet(sessionFactory)), "/list");
		context.addServlet(new ServletHolder(new CreateUserServlet(sessionFactory)), "/create");

		context.addFilter(new FilterHolder(new AuthFilter()), "/admin", null);
		context.addFilter(new FilterHolder(new AuthFilter()), "/create", null);
		context.addFilter(new FilterHolder(new AuthFilter()), "/list", null);

		Server server = new ServerBuilder(context)
				.withPort(8080)
				.withPathResource("/static")
				.build();

		server.start();
		server.join();
	}
}
