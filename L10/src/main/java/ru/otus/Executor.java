package ru.otus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Executor {
	private static final String URL = "jdbc:h2:mem:";
	private final Connection connection;

	Executor() throws SQLException {
		connection = DriverManager.getConnection(URL);
		connection.setAutoCommit(false);
	}

	void createTableUser() throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("create table User(id bigint(20), name varchar(255), age int(3))")) {
			int result = preparedStatement.executeUpdate();
			System.out.println("Create table User: " + result);
		}
	}

	void closeConnection() throws SQLException {
		connection.close();
	}
}
