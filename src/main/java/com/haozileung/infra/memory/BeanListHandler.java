package com.haozileung.infra.memory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BeanListHandler<T> implements ResultSetHandler<List<T>> {

	private final Class<T> type;

	private BeanProcessor convert = new BeanProcessor();

	public BeanListHandler(Class<T> type) {
		this.type = type;
		this.convert = new BeanProcessor();
	}

	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		return this.convert.toBeanList(rs, type);
	}
}