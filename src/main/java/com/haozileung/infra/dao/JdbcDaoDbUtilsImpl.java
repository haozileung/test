package com.haozileung.infra.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;
import com.haozileung.infra.dal.BoundSql;
import com.haozileung.infra.dal.ConnectionManager;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.SqlFactory;
import com.haozileung.infra.dal.build.AutoField;
import com.haozileung.infra.dal.build.Criteria;
import com.haozileung.infra.dal.exceptions.JdbcAssistantException;
import com.haozileung.infra.dal.handler.NameHandler;

public class JdbcDaoDbUtilsImpl implements JdbcDao {

	/**
	 * 数据库方言
	 */
	protected String dialect;

	/**
	 * 名称处理器，为空按默认执行
	 */
	@Inject
	protected NameHandler nameHandler;

	/**
	 * QueryRunner 对象
	 */
	@Inject
	protected QueryRunner runner;

	/**
	 * 自定义sql处理
	 */
	@Inject
	protected SqlFactory sqlFactory;

	@Inject
	protected ConnectionManager connectionManager;

	public int delete(Class<?> clazz, Long id) {
		BoundSql boundSql = Criteria.delete(clazz).where(getNameHandler().getPkFieldName(clazz), new Object[] { id })
				.build(true, getNameHandler());
		try {
			return this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public int delete(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		try {
			return this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public int delete(Object entity) {
		BoundSql boundSql = Criteria.delete(entity.getClass()).build(entity, true, getNameHandler());
		try {
			return this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public void deleteAll(Class<?> clazz) {
		String tableName = this.getNameHandler().getTableName(clazz);
		String sql = "TRUNCATE TABLE " + tableName;
		try {
			this.runner.update(connectionManager.getConnection(), sql);
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public <T> T get(Class<T> clazz, Long id) {
		BoundSql boundSql = Criteria.select(clazz).where(getNameHandler().getPkFieldName(clazz), new Object[] { id })
				.build(true, getNameHandler());
		// 采用list方式查询，当记录不存在时返回null而不会抛出异常
		List<T> list = null;
		try {
			list = this.runner.query(boundSql.getSql(), new BeanListHandler<T>(clazz),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.iterator().next();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Criteria criteria, Long id) {
		BoundSql boundSql = criteria
				.where(getNameHandler().getPkFieldName(criteria.getEntityClass()), new Object[] { id })
				.build(true, getNameHandler());
		// 采用list方式查询，当记录不存在时返回null而不会抛出异常
		List<T> list = null;
		try {
			list = (List<T>) this.runner.query(boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) criteria.getEntityClass()), id);
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.iterator().next();
	}

	protected String getDialect() {
		if (StringUtils.isBlank(dialect)) {
			try {
				dialect = this.connectionManager.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
			} catch (Exception e) {
				return null;
			}
		}
		return dialect;

	}

	/**
	 * 获取名称处理器
	 *
	 * @return
	 */
	protected NameHandler getNameHandler() {
		return this.nameHandler;
	}

	/**
	 * 获取QueryRunner
	 *
	 * @return
	 */
	protected QueryRunner getRunner() {
		return this.runner;
	}

	protected SqlFactory getSqlFactory() {
		return this.sqlFactory;
	}

	/**
	 * 插入数据
	 *
	 * @param boundSql
	 *            the bound build
	 * @return long long
	 */
	protected Long insert(final BoundSql boundSql) {
		try {
			return this.runner.insert(connectionManager.getConnection(), boundSql.getSql(), new ScalarHandler<Long>(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public Long insert(Criteria criteria) {
		final BoundSql boundSql = criteria.build(true, getNameHandler());
		return this.insert(boundSql);
	}

	public Long insert(Object entity) {
		NameHandler handler = this.getNameHandler();
		Criteria criteria = Criteria.insert(entity.getClass());
		String nativePKValue = handler.getPkNativeValue(entity.getClass(), getDialect());
		if (StringUtils.isNotBlank(nativePKValue)) {
			String pkFieldName = handler.getPkFieldName(entity.getClass());
			criteria.into(AutoField.NATIVE_FIELD_TOKEN[0] + pkFieldName + AutoField.NATIVE_FIELD_TOKEN[1],
					nativePKValue);
		}
		final BoundSql boundSql = criteria.build(entity, true, getNameHandler());
		return this.insert(boundSql);
	}

	public int queryCount(Criteria criteria) {
		BoundSql boundSql = criteria.addSelectFunc("count(*)").build(true, getNameHandler());
		try {
			return this.runner.query(connectionManager.getConnection(), boundSql.getSql(), new ScalarHandler<Integer>(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public int queryCount(Object entity) {
		BoundSql boundSql = Criteria.select(entity.getClass()).addSelectFunc("count(*)").build(entity, true,
				getNameHandler());
		try {
			return this.runner.query(connectionManager.getConnection(), boundSql.getSql(), new ScalarHandler<Integer>(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public int queryCount(Object entity, Criteria criteria) {
		BoundSql boundSql = criteria.addSelectFunc("count(*)").build(entity, true, getNameHandler());
		try {
			return this.runner.query(boundSql.getSql(), new ScalarHandler<Integer>(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public List<Map<String, Object>> queryForList(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		List<Map<String, Object>> mapList = null;
		try {
			mapList = this.runner.query(connectionManager.getConnection(), boundSql.getSql(), new MapListHandler(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
		return mapList;
	}

	public <T> T queryForObject(Criteria criteria) {
		return querySingleResult(criteria);
	}

	public List<Map<String, Object>> queryForSql(String refSql) {
		return this.queryForSql(refSql, null, null);
	}

	public List<Map<String, Object>> queryForSql(String refSql, Object[] params) {
		return this.queryForSql(refSql, null, params);
	}

	public List<Map<String, Object>> queryForSql(String refSql, String expectParamKey, Object[] params) {
		BoundSql boundSql = this.sqlFactory.getBoundSql(refSql, expectParamKey, params);
		List<Map<String, Object>> mapList = null;
		try {
			mapList = this.runner.query(connectionManager.getConnection(), boundSql.getSql(), new MapListHandler(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
		return mapList;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(Class<?> clazz) {
		BoundSql boundSql = Criteria.select(clazz).build(true, getNameHandler());
		List<?> list = null;
		try {
			list = this.runner.query(connectionManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) clazz), boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
		return (List<T>) list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		List<T> list = null;
		try {
			list = this.runner.query(connectionManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) criteria.getEntityClass()), boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(T entity) {
		BoundSql boundSql = Criteria.select(entity.getClass()).build(entity, true, getNameHandler());
		List<T> list = null;
		try {
			list = this.runner.query(connectionManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()), boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(T entity, Criteria criteria) {
		BoundSql boundSql = criteria.build(entity, true, getNameHandler());
		List<T> list = null;
		try {
			list = this.runner.query(connectionManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()), boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> T querySingleResult(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		// 采用list方式查询，当记录不存在时返回null而不会抛出异常
		List<?> list = null;
		try {
			list = this.runner.query(connectionManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) criteria.getEntityClass()), boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return (T) list.iterator().next();
	}

	@SuppressWarnings("unchecked")
	public <T> T querySingleResult(T entity) {
		BoundSql boundSql = Criteria.select(entity.getClass()).build(entity, true, getNameHandler());
		// 采用list方式查询，当记录不存在时返回null而不会抛出异常
		List<T> list = null;
		try {
			list = (List<T>) this.runner.query(connectionManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()), boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.iterator().next();
	}

	public void save(Criteria criteria) {
		final BoundSql boundSql = criteria.build(true, getNameHandler());
		try {
			this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
	}

	public void save(Object entity) {
		final BoundSql boundSql = Criteria.insert(entity.getClass()).build(entity, true, getNameHandler());
		try {
			this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {

			throw new JdbcAssistantException(e);
		}
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public void setNameHandler(NameHandler nameHandler) {
		this.nameHandler = nameHandler;
	}

	public void setRunner(QueryRunner runner) {
		this.runner = runner;
	}

	public void setSqlFactory(SqlFactory sqlFactory) {
		this.sqlFactory = sqlFactory;
	}

	public int update(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		try {
			return this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public int update(Object entity) {
		BoundSql boundSql = Criteria.update(entity.getClass()).build(entity, true, getNameHandler());
		try {
			return this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public int update(Object entity, boolean isIgnoreNull) {
		BoundSql boundSql = Criteria.update(entity.getClass()).build(entity, isIgnoreNull, getNameHandler());
		try {
			return this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}

	public int updateForSql(String refSql) {
		return this.updateForSql(refSql, null, null);
	}

	public int updateForSql(String refSql, Object[] params) {
		return this.updateForSql(refSql, null, params);
	}

	public int updateForSql(String refSql, String expectParamKey, Object[] params) {
		BoundSql boundSql = this.sqlFactory.getBoundSql(refSql, expectParamKey, params);
		try {
			return this.runner.update(connectionManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (Exception e) {
			throw new JdbcAssistantException(e);
		}
	}
}
