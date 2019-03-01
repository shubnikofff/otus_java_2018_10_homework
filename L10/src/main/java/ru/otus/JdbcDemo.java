package ru.otus;

import java.sql.SQLException;

public class JdbcDemo {
	public static void main(String[] args) throws SQLException {
		Executor executor = new Executor();
		executor.createTableUser();
		executor.closeConnection();
	}
}
