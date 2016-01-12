package com.haozileung.infra.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于跟踪执行的SQL语句
 *
 * @author Winter Lau
 */
class DebugConnection implements InvocationHandler {

	private final Logger log = LoggerFactory.getLogger(DebugConnection.class);
	private Connection conn = null;

	public DebugConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Returns the conn.
	 *
	 * @return Connection
	 */
	public Connection getConnection() {
		return (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(),
				this);
	}

	@Override
	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
		try {
			String method = m.getName();
			if ("prepareStatement".equals(method) || "createStatement".equals(method))
				log.info("[SQL] >>> {}", args[0]);
			return m.invoke(conn, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}