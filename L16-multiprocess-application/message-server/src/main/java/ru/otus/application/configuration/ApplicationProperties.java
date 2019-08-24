package ru.otus.application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {
	private List<Server> servers = new ArrayList<>();
	private List<String> dbServerIds = new ArrayList<>();

	public List<Server> getServers() {
		return servers;
	}

	public List<String> getDbServerIds() {
		return dbServerIds;
	}

	public static class Server {
		private String id;
		private int port;
		private String command;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getCommand() {
			return command;
		}

		public void setCommand(String command) {
			this.command = command;
		}
	}
}
