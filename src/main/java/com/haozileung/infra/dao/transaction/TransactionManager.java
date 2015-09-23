package com.haozileung.infra.dao.transaction;

import com.haozileung.infra.dao.exceptions.DaoException;
import com.haozileung.infra.utils.DataSourceUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private Connection conn;

    public TransactionManager(Connection conn) {
        this.conn = conn;
    }

    /**
     * 开启事务
     */
    public void beginTransaction() throws DaoException {
        try {
            conn.setAutoCommit(false); // 把事务提交方式改为手工提交
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), "开户事务时出现异常");
        }
    }

    /**
     * 提交事务并关闭连接
     */
    public void commitAndClose() throws DaoException {
        try {
            conn.commit(); // 提交事务
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), "提交事务时出现异常");
        } finally {
            DataSourceUtil.closeConnection();
        }
    }

    /**
     * 回滚并关闭连接
     */
    public void rollbackAndClose() throws DaoException {
        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new DaoException(e.getSQLState(), "回滚事务时出现异常");
        } finally {
            DataSourceUtil.closeConnection();
        }
    }
}