package ru.otus.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class ServerBuilder {
	private ContextHandler contextHandler;
	private int port = 80;
	private String pathResource = "/public";

	public ServerBuilder(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	public ServerBuilder withPort(int port) {
		this.port = port;
		return this;
	}

	public ServerBuilder withPathResource(String pathResource) {
		this.pathResource = pathResource;
		return this;
	}

	public Server build() {
		ResourceHandler resourceHandler = new ResourceHandler();
		Resource resource = Resource.newClassPathResource(pathResource);
		resourceHandler.setBaseResource(resource);

		Server server = new Server(port);
		server.setHandler(new HandlerList(resourceHandler, contextHandler));
		return server;
	}
}
