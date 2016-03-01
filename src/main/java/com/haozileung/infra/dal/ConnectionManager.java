package com.haozileung.infra.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.haozileung.web.init.AppInitializer;

public class ConnectionManager {
	private final ThreadLocal<Map<String, Connection>> connections = new ThreadLocal<Map<String, Connection>>();

	public Connection getConnection() throws SQLException {
		Map<String, Connection> connMap = connections.get();
		if (connMap == null) {
			connMap = Maps.newHashMap();
		}
		String name = MoreObjects.firstNonNull(DataSourceHolder.getName(), "read");
		Connection conn = connMap.get(name);
		if (conn == null || conn.isClosed()) {
			DataSource dataSource = AppInitializer.getInjector()
					.getInstance(Key.get(DataSource.class, Names.named(name)));
			if (dataSource == null) {
				return null;
			}
			conn = dataSource.getConnection();
			connMap.put(name, conn);
			connections.set(connMap);
		}
		return conn;
	}

	public Map<String, Connection> getConnections() {
		return connections.get();
	}
}