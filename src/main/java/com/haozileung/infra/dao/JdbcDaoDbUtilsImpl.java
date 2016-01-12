package com.haozileung.infra.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.haozileung.infra.dal.BoundSql;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.SqlFactory;
import com.haozileung.infra.dal.build.AutoField;
import com.haozileung.infra.dal.build.Criteria;
import com.haozileung.infra.dal.handler.NameHandler;

public class JdbcDaoDbUtilsImpl implements JdbcDao {

	private static Logger logger = LoggerFactory.getLogger(JdbcDaoDbUtilsImpl.class);

	/**
	 * QueryRunner 对象
	 */
	@Inject
	protected QueryRunner runner;

	/**
	 * 名称处理器，为空按默认执行
	 */
	@Inject
	protected NameHandler nameHandler;

	/**
	 * 自定义sql处理
	 */
	@Inject
	protected SqlFactory sqlFactory;

	@Inject
	protected DataSourceManager dataSourceManager;

	/**
	 * 数据库方言
	 */
	protected String dialect;

	protected String getDialect() {
		if (StringUtils.isBlank(dialect)) {
			try {
				dialect = dataSourceManager.getConnection().getMetaData().getDatabaseProductName().toUpperCase();
			} catch (SQLException e) {
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

	/**
	 * 插入数据
	 *
	 * @param boundSql
	 *            the bound build
	 * @return long long
	 */
	protected Long insert(final BoundSql boundSql, final Class<?> clazz) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = dataSourceManager.getConnection().prepareStatement(boundSql.getSql(),
					PreparedStatement.RETURN_GENERATED_KEYS);
			Object[] params = boundSql.getParameters().toArray();
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			return rs.next() ? (Long) rs.getObject(1) : null;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return 0L;
		} finally {
			dataSourceManager.closeConnection();
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

	protected SqlFactory getSqlFactory() {
		return this.sqlFactory;
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
		return this.insert(boundSql, entity.getClass());
	}

	public Long insert(Criteria criteria) {
		final BoundSql boundSql = criteria.build(true, getNameHandler());
		return this.insert(boundSql, criteria.getEntityClass());
	}

	public void save(Object entity) {
		final BoundSql boundSql = Criteria.insert(entity.getClass()).build(entity, true, getNameHandler());
		try {
			getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
	}

	public void save(Criteria criteria) {
		final BoundSql boundSql = criteria.build(true, getNameHandler());
		try {
			getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
	}

	public int update(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		try {
			return getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public int update(Object entity) {
		BoundSql boundSql = Criteria.update(entity.getClass()).build(entity, true, getNameHandler());
		try {
			return getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public int update(Object entity, boolean isIgnoreNull) {
		BoundSql boundSql = Criteria.update(entity.getClass()).build(entity, isIgnoreNull, getNameHandler());
		try {
			return getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public int delete(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		try {
			return getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public int delete(Object entity) {
		BoundSql boundSql = Criteria.delete(entity.getClass()).build(entity, true, getNameHandler());
		try {
			return getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public int delete(Class<?> clazz, Long id) {
		BoundSql boundSql = Criteria.delete(clazz).where(getNameHandler().getPkFieldName(clazz), new Object[] { id })
				.build(true, getNameHandler());
		try {
			return getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public void deleteAll(Class<?> clazz) {
		String tableName = this.getNameHandler().getTableName(clazz);
		String sql = "TRUNCATE TABLE " + tableName;
		try {
			getRunner().update(dataSourceManager.getConnection(), sql);
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		List<T> list = null;
		try {
			list = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) criteria.getEntityClass()), boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(Class<?> clazz) {
		BoundSql boundSql = Criteria.select(clazz).build(true, getNameHandler());
		List<?> list = null;
		try {
			list = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) clazz), boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		return (List<T>) list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(T entity) {
		BoundSql boundSql = Criteria.select(entity.getClass()).build(entity, true, getNameHandler());
		List<T> list = null;
		try {
			list = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()), boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(T entity, Criteria criteria) {
		BoundSql boundSql = criteria.build(entity, true, getNameHandler());
		List<T> list = null;
		try {
			list = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()), boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public int queryCount(Object entity, Criteria criteria) {
		BoundSql boundSql = criteria.addSelectFunc("count(*)").build(entity, true, getNameHandler());
		try {
			return getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(), new ScalarHandler<Integer>(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public int queryCount(Object entity) {
		BoundSql boundSql = Criteria.select(entity.getClass()).addSelectFunc("count(*)").build(entity, true,
				getNameHandler());
		try {
			return getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(), new ScalarHandler<Integer>(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public int queryCount(Criteria criteria) {
		BoundSql boundSql = criteria.addSelectFunc("count(*)").build(true, getNameHandler());
		try {
			return getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(), new ScalarHandler<Integer>(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public <T> T get(Class<T> clazz, Long id) {
		BoundSql boundSql = Criteria.select(clazz).where(getNameHandler().getPkFieldName(clazz), new Object[] { id })
				.build(true, getNameHandler());
		// 采用list方式查询，当记录不存在时返回null而不会抛出异常
		List<T> list = null;
		try {
			list = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>(clazz), boundSql.getParameters().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
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
			list = (List<T>) getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) criteria.getEntityClass()), id);
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.iterator().next();
	}

	@SuppressWarnings("unchecked")
	public <T> T querySingleResult(T entity) {
		BoundSql boundSql = Criteria.select(entity.getClass()).build(entity, true, getNameHandler());
		// 采用list方式查询，当记录不存在时返回null而不会抛出异常
		List<T> list = null;
		try {
			list = (List<T>) getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()), boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.iterator().next();
	}

	@SuppressWarnings("unchecked")
	public <T> T querySingleResult(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		// 采用list方式查询，当记录不存在时返回null而不会抛出异常
		List<?> list = null;
		try {
			list = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) criteria.getEntityClass()), boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return (T) list.iterator().next();
	}

	public <T> T queryForObject(Criteria criteria) {
		return querySingleResult(criteria);
	}

	public List<Map<String, Object>> queryForList(Criteria criteria) {
		BoundSql boundSql = criteria.build(true, getNameHandler());
		List<Map<String, Object>> mapList = null;
		try {
			mapList = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(), new MapListHandler(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		return mapList;
	}

	public List<Map<String, Object>> queryForSql(String refSql) {
		return this.queryForSql(refSql, null, null);
	}

	public List<Map<String, Object>> queryForSql(String refSql, Object[] params) {
		return this.queryForSql(refSql, null, params);
	}

	public List<Map<String, Object>> queryForSql(String refSql, String expectParamKey, Object[] params) {
		BoundSql boundSql = getSqlFactory().getBoundSql(refSql, expectParamKey, params);
		List<Map<String, Object>> mapList = null;
		try {
			mapList = getRunner().query(dataSourceManager.getConnection(), boundSql.getSql(), new MapListHandler(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
		}
		return mapList;
	}

	public int updateForSql(String refSql) {
		return this.updateForSql(refSql, null, null);
	}

	public int updateForSql(String refSql, Object[] params) {
		return this.updateForSql(refSql, null, params);
	}

	public int updateForSql(String refSql, String expectParamKey, Object[] params) {
		BoundSql boundSql = getSqlFactory().getBoundSql(refSql, expectParamKey, params);
		try {
			return getRunner().update(dataSourceManager.getConnection(), boundSql.getSql(),
					boundSql.getParameters().toArray());
		} catch (SQLException e) {

			logger.error(e.getMessage(), e);
			return 0;
		}
	}
}
