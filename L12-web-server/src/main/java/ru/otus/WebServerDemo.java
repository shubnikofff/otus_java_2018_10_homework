package ru.otus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;

public class WebServerDemo {
	private final static int PORT = 8080;
	private final static String PUBLIC_HTML = "/static";

	public static void main(String[] args) throws Exception {
		new WebServerDemo().start();
	}

	private void start() throws Exception {
		System.out.println("Web Server...");
		ResourceHandler resourceHandler = new ResourceHandler();
		Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
		resourceHandler.setBaseResource(resource);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		Server server = new Server(PORT);
		server.setHandler(new HandlerList(resourceHandler, context));

		server.start();
		server.join();
	}
}
