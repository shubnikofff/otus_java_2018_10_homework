package ru.otus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcDemo {
	private static final String DB_URL = "jdbc:h2:mem:";

	public static void main(String[] args) throws SQLException {
		Connection connection = DriverManager.getConnection(DB_URL);
		connection.setAutoCommit(false);

		createTableUser(connection);

//		DbExecutor dbExecutor = new DbExecutor(connection);
		Executor<User> executor = new Executor<>(connection);
		executor.load(1, User.class);

		connection.close();
	}

	static private void createTableUser(Connection connection) throws SQLException {
		try(PreparedStatement preparedStatement = connection.prepareStatement("create table User(id bigint(20), name varchar(255), age int(3))")) {
			preparedStatement.executeUpdate();
		}
	}
}
