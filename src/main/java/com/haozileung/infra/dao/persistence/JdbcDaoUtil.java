package com.haozileung.infra.dao.persistence;

public class JdbcDaoUtil {

	private static class JdbcDaoHolder {
		private static final JdbcDao INSTANCE = new JdbcDaoDbUtilsImpl();
	}

	private JdbcDaoUtil() {
	}

	public static final JdbcDao getInstance() {
		return JdbcDaoHolder.INSTANCE;
	}

}
