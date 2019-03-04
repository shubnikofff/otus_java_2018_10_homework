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

		Executor<User> executor = new Executor<>(connection);

		User newUser = new User(1, "Bill", 34);
		executor.save(newUser);

		User loadedUser = executor.load(1, User.class);
		loadedUser.setAge(35);

		executor.save(loadedUser);
		User updatedUser = executor.load(1, User.class);

		connection.close();
	}

	static private void insertUser(Connection connection) throws SQLException {
		try (PreparedStatement pst = connection.prepareStatement("insert into User(name, age) values (?, ?)")) {
			pst.setString(1, "Ivan");
			pst.setInt(2, 34);
			pst.executeUpdate();
			connection.commit();
		}
	}

	static private void createTableUser(Connection connection) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
			preparedStatement.executeUpdate();
			connection.commit();
		}
	}
}
