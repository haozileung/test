package com.haozileung.infra.dao.persistence;

public class JdbcDaoUtil {

    private JdbcDaoUtil() {
    }

    public static final JdbcDao getInstance() {
        return JdbcDaoHolder.INSTANCE;
    }

    private static class JdbcDaoHolder {
        private static final JdbcDao INSTANCE = new JdbcDaoDbUtilsImpl();
    }

}
