package com.haozileung.infra.dal;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TxManager {
    private final static Logger logger = LoggerFactory.getLogger(TxManager.class);
    @Inject
    private ConnectionManager connectionManager;

    public void beginTransation() {
        try {
            Connection conn = connectionManager.getConnection();
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void rollback() {
        try {
            Connection conn = connectionManager.getConnection();
            conn.rollback();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void close() {
        try {
            Connection conn = connectionManager.getConnection();
            conn.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}