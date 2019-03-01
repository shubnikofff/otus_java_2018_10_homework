package ru.otus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;

class DbExecutor<T> {
	private final Connection connection;

	DbExecutor(Connection connection) {
		this.connection = connection;
	}

	Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> resultSetHandler) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return Optional.ofNullable(resultSetHandler.apply(resultSet));
			}
		}
	}

//	int executeUpdate(String sql) throws SQLException {
//		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//			return preparedStatement.executeUpdate();
//		}
//	}
//
//	ResultSet executeQuery(String sql) throws SQLException {
//		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//			return preparedStatement.executeQuery();
//		}
//	}


//	Optional<T> selectUser(long id, Function<ResultSet, T> resultSetHandler) throws SQLException {
//		try (PreparedStatement preparedStatement = connection.prepareStatement("select id, name, age from user where where id = ?")) {
//			preparedStatement.setLong(1, id);
//			try (ResultSet resultSet = preparedStatement.executeQuery()) {
//				return Optional.ofNullable(resultSetHandler.apply(resultSet));
//			}
//		}
//	}
}
