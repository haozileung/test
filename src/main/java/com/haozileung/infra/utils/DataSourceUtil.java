package com.haozileung.infra.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.haozileung.infra.dao.persistence.TransactionManager;

public class DataSourceUtil {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);
	private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	private static DruidDataSource druidDataSource;

	private static boolean show_sql = true;

	public final static Connection getConnection() throws SQLException {
		if (null == druidDataSource) {
			druidDataSource = new DruidDataSource();
			druidDataSource.setUrl(PropertiesUtil.getProperties().getProperty("db.url"));
			druidDataSource.setUsername(PropertiesUtil.getProperties().getProperty("db.username"));
			druidDataSource.setPassword(PropertiesUtil.getProperties().getProperty("db.password"));
			druidDataSource.setInitialSize(1);
			druidDataSource.setMinIdle(1);
			druidDataSource.setMaxActive(20);
			druidDataSource.setMaxWait(6000);
			druidDataSource.setTimeBetweenEvictionRunsMillis(6000);
			druidDataSource.setMinEvictableIdleTimeMillis(30000);
			druidDataSource.setValidationQuery("SELECT 'x'");
			druidDataSource.setTestWhileIdle(true);
			druidDataSource.setTestOnBorrow(false);
			druidDataSource.setTestOnReturn(false);
			druidDataSource.setUseGlobalDataSourceStat(true);
			try {
				druidDataSource.setFilters("stat,wall");
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			try {
				druidDataSource.init();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			String show_sql_prop = PropertiesUtil.getProperties().getProperty("show_sql");
			show_sql = ((show_sql_prop != null) && show_sql_prop.equalsIgnoreCase("true"));
		}
		Connection conn = conns.get();
		if (conn == null || conn.isClosed()) {
			conn = druidDataSource.getConnection();
			conn.setAutoCommit(false);
			conns.set(conn);
		}
		return (show_sql && !Proxy.isProxyClass(conn.getClass())) ? new _DebugConnection(conn).getConnection() : conn;
	}

	/**
	 * 获取事务管理器
	 * 
	 * @return 事务管理实例
	 */
	public static synchronized TransactionManager getTranManager() {
		try {
			return new TransactionManager(getConnection());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 关闭连接
	 */
	public final static void closeConnection() {
		Connection conn = conns.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Unabled to close connection!!! ", e);
			}
		}
		conns.set(null);
	}

	public static void destroy() {
		druidDataSource.close();
	}

	/**
	 * 用于跟踪执行的SQL语句
	 *
	 * @author Winter Lau
	 */
	static class _DebugConnection implements InvocationHandler {

		private final static Logger log = LoggerFactory.getLogger(_DebugConnection.class);
		private Connection conn = null;

		public _DebugConnection(Connection conn) {
			this.conn = conn;
		}

		/**
		 * Returns the conn.
		 *
		 * @return Connection
		 */
		public Connection getConnection() {
			return (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(),
					conn.getClass().getInterfaces(), this);
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
}
