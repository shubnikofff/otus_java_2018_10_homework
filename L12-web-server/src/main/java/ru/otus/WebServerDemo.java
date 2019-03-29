package ru.otus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.filter.AuthFilter;
import ru.otus.server.ServerBuilder;
import ru.otus.servlet.LoginServlet;
import ru.otus.servlet.UserListServlet;

public class WebServerDemo {
	public static void main(String[] args) throws Exception {
		new WebServerDemo().startWebServer();
	}

	private void startWebServer() throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(new ServletHolder(new LoginServlet()), "/login");
		context.addServlet(new ServletHolder(new UserListServlet()), "/user-list");
		context.addFilter(new FilterHolder(new AuthFilter()), "/user-list", null);

		Server server = new ServerBuilder(context)
				.withPort(8080)
				.withPathResource("static")
				.build();

		server.start();
		server.join();
	}
}
