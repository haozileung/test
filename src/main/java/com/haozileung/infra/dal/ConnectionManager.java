package com.haozileung.infra.dal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.google.inject.Inject;

public class ConnectionManager {
	private final ThreadLocal<Connection> connection = new ThreadLocal<Connection>();

	@Inject
	private DataSource dataSource;

	public Connection getConnection() throws SQLException {
		Connection conn = connection.get();
		if (conn == null) {
			conn = dataSource.getConnection();
			connection.set(conn);
		}
		return conn;
	}
}