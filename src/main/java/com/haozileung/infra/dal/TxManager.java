package com.haozileung.infra.dal;

import java.sql.Connection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.haozileung.infra.dal.exceptions.JdbcAssistantException;

public class TxManager implements MethodInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(TxManager.class);
	@Inject
	private ConnectionManager connectionManager;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Connection conn = connectionManager.getConnection();
		conn.setAutoCommit(false);
		Object result = null;
		try {
			result = invocation.proceed();
			conn.commit();
		} catch (JdbcAssistantException ex) {
			logger.error(ex.getMessage());
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}
}