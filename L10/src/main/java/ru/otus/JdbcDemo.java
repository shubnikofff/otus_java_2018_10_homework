package ru.otus;

import java.sql.SQLException;

public class JdbcDemo {
	public static void main(String[] args) throws SQLException {
		DbManager dbManager = new DbManager();
		dbManager.createTableUser();
		dbManager.closeConnection();
	}
}
