package ru.otus.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.hibernate.SessionFactory;
import ru.otus.filter.AuthFilter;
import ru.otus.service.FrontendService;
import ru.otus.servlet.AdminServlet;
import ru.otus.servlet.CreateUserServlet;
import ru.otus.servlet.ListUserServlet;
import ru.otus.servlet.LoginServlet;

public class ServerBuilder {
	private final ResourceHandler resourceHandler = new ResourceHandler();
	private final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

	public ServerBuilder withResourceHandler(String resourcePath) {
		resourceHandler.setBaseResource(Resource.newClassPathResource(resourcePath));
		return this;
	}

	public ServerBuilder withContextHandler(SessionFactory sessionFactory, FrontendService frontendService) {
		contextHandler.addServlet(new ServletHolder(new AdminServlet()), "/admin");
		contextHandler.addServlet(new ServletHolder(new LoginServlet(frontendService)), "/login");
		contextHandler.addServlet(new ServletHolder(new ListUserServlet(sessionFactory)), "/list");
		contextHandler.addServlet(new ServletHolder(new CreateUserServlet(frontendService)), "/create");

		contextHandler.addFilter(new FilterHolder(new AuthFilter()), "/admin", null);
		contextHandler.addFilter(new FilterHolder(new AuthFilter()), "/create", null);
		contextHandler.addFilter(new FilterHolder(new AuthFilter()), "/list", null);

		return this;
	}

	public Server build(int port) {
		final Server server = new Server(port);
		server.setHandler(new HandlerList(resourceHandler, contextHandler));

		return server;
	}
}
