package ru.otus.application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {
	private SystemAdmin systemAdmin;

	public void setSystemAdmin(SystemAdmin systemAdmin) {
		this.systemAdmin = systemAdmin;
	}

	public SystemAdmin getSystemAdmin() {
		return systemAdmin;
	}

	public static class SystemAdmin {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
