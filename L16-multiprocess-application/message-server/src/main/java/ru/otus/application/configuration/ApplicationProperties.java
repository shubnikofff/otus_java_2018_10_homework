package ru.otus.application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {
	private List<Client> clients = new ArrayList<>();
	private List<String> dbServerIds = new ArrayList<>();
	private String logDirectory;
	private String commandLineSeparator;
	private int sleepTimeBeforeConnectToClient;

	public List<Client> getClients() {
		return clients;
	}

	public List<String> getDbServerIds() {
		return dbServerIds;
	}

	public String getLogDirectory() {
		return logDirectory;
	}

	public void setLogDirectory(String logDirectory) {
		this.logDirectory = logDirectory;
	}

	public String getCommandLineSeparator() {
		return commandLineSeparator;
	}

	public void setCommandLineSeparator(String commandLineSeparator) {
		this.commandLineSeparator = commandLineSeparator;
	}

	public int getSleepTimeBeforeConnectToClient() {
		return sleepTimeBeforeConnectToClient;
	}

	public void setSleepTimeBeforeConnectToClient(int sleepTimeBeforeConnectToClient) {
		this.sleepTimeBeforeConnectToClient = sleepTimeBeforeConnectToClient;
	}

	public static class Client {
		private String id;
		private int port;
		private String runCommand;

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

		public String getRunCommand() {
			return runCommand;
		}

		public void setRunCommand(String runCommand) {
			this.runCommand = runCommand;
		}
	}
}
