package com.haozileung.infra.dao.persistence;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.dao.pager.Pager;
import com.haozileung.infra.utils.DataSourceUtils;
import com.haozileung.infra.utils.NameUtil;

public class JdbcDaoDbUtilsImpl implements JdbcDao {

	private static final Logger logger = LoggerFactory
			.getLogger(JdbcDaoDbUtilsImpl.class);

	/**
	 * runner 对象
	 */
	private static QueryRunner runner = new QueryRunner();

	/**
	 * 名称处理器，为空按默认执行
	 */
	private NameHandler nameHandler = new DefaultNameHandler();

	/**
	 * 插入数据
	 *
	 * @param entity
	 *            the entity
	 * @param criteria
	 *            the criteria
	 * @return long long
	 */
	private Long insert(Object entity, Criteria criteria) {
		Class<?> entityClass = SqlAssembleUtils
				.getEntityClass(entity, criteria);
		NameHandler handler = this.getNameHandler();
		String pkValue = null;
		if (StringUtils.isNotBlank(pkValue)) {
			String primaryName = handler.getPKName(entityClass);
			if (criteria == null) {
				criteria = Criteria.create(entityClass);
			}
			criteria.setPKValueName(NameUtil.getCamelName(primaryName), pkValue);
		}
		final BoundSql boundSql = SqlAssembleUtils.buildInsertSql(entity,
				criteria, this.getNameHandler());
		try {
			return runner.insert(DataSourceUtils.getConnection(), boundSql
					.getSql(), new ScalarHandler<Long>(), boundSql.getParams()
					.toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Long insert(Object entity) {
		return this.insert(entity, null);
	}

	@Override
	public Long insert(Criteria criteria) {
		return this.insert(null, criteria);
	}

	@Override
	public void save(Object entity) {
		final BoundSql boundSql = SqlAssembleUtils.buildInsertSql(entity, null,
				this.getNameHandler());
		try {
			runner.update(DataSourceUtils.getConnection(),
					boundSql.getSql(), boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void save(Criteria criteria) {
		final BoundSql boundSql = SqlAssembleUtils.buildInsertSql(null,
				criteria, this.getNameHandler());
		try {
			runner.update(DataSourceUtils.getConnection(),
					boundSql.getSql(), boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void update(Criteria criteria) {
		BoundSql boundSql = SqlAssembleUtils.buildUpdateSql(null, criteria,
				this.getNameHandler());
		try {
			runner.update(DataSourceUtils.getConnection(),
					boundSql.getSql(), boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void update(Object entity) {
		BoundSql boundSql = SqlAssembleUtils.buildUpdateSql(entity, null,
				this.getNameHandler());
		try {
			runner.update(DataSourceUtils.getConnection(),
					boundSql.getSql(), boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void delete(Criteria criteria) {
		BoundSql boundSql = SqlAssembleUtils.buildDeleteSql(null, criteria,
				this.getNameHandler());
		try {
			runner.update(DataSourceUtils.getConnection(),
					boundSql.getSql(), boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void delete(Object entity) {
		BoundSql boundSql = SqlAssembleUtils.buildDeleteSql(entity, null,
				this.getNameHandler());
		try {
			runner.update(DataSourceUtils.getConnection(),
					boundSql.getSql(), boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void delete(Class<?> clazz, Long id) {
		BoundSql boundSql = SqlAssembleUtils.buildDeleteSql(clazz, id,
				this.getNameHandler());
		try {
			runner.update(DataSourceUtils.getConnection(),
					boundSql.getSql(), boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAll(Class<?> clazz) {
		String tableName = this.getNameHandler().getTableName(clazz);
		String sql = "TRUNCATE TABLE " + tableName;
		try {
			runner.update(DataSourceUtils.getConnection(), sql);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(Criteria criteria) {
		BoundSql boundSql = SqlAssembleUtils.buildListSql(null, criteria,
				this.getNameHandler());
		List<T> list = null;
		try {
			list = runner
					.query(DataSourceUtils.getConnection(), boundSql
							.getSql(), new BeanListHandler<T>(
							(Class<T>) criteria.getEntityClass()), boundSql
							.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return (List<T>) list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(T entity) {
		BoundSql boundSql = SqlAssembleUtils.buildListSql(entity, null,
				this.getNameHandler());
		List<T> list = null;
		try {
			list = runner.query(DataSourceUtils.getConnection(),
					boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()),
					boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return (List<T>) list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(T entity, Criteria criteria) {
		BoundSql boundSql = SqlAssembleUtils.buildListSql(entity, criteria,
				this.getNameHandler());
		List<T> list = null;
		try {
			list = runner.query(DataSourceUtils.getConnection(),
					boundSql.getSql(),
					new BeanListHandler<T>((Class<T>) entity.getClass()),
					boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return (List<T>) list;
	}

	@Override
	public int queryCount(Object entity, Criteria criteria) {
		BoundSql boundSql = SqlAssembleUtils.buildCountSql(entity, criteria,
				this.getNameHandler());
		try {
			return runner.query(DataSourceUtils.getConnection(), boundSql
					.getSql(), new ScalarHandler<Integer>(), boundSql
					.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int queryCount(Object entity) {
		BoundSql boundSql = SqlAssembleUtils.buildCountSql(entity, null,
				this.getNameHandler());
		try {
			return runner.query(DataSourceUtils.getConnection(), boundSql
					.getSql(), new ScalarHandler<Integer>(), boundSql
					.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public int queryCount(Criteria criteria) {
		BoundSql boundSql = SqlAssembleUtils.buildCountSql(null, criteria,
				this.getNameHandler());
		try {
			return runner.query(DataSourceUtils.getConnection(), boundSql
					.getSql(), new ScalarHandler<Integer>(), boundSql
					.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public <T> T get(Class<T> clazz, Long id) {
		BoundSql boundSql = SqlAssembleUtils.buildByIdSql(clazz, id, null,
				this.getNameHandler());
		try {
			return runner.query(DataSourceUtils.getConnection(),
					boundSql.getSql(), new BeanHandler<T>(clazz), id);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Criteria criteria, Long id) {
		BoundSql boundSql = SqlAssembleUtils.buildByIdSql(null, id, criteria,
				this.getNameHandler());
		try {
			return runner.query(DataSourceUtils.getConnection(),
					boundSql.getSql(),
					new BeanHandler<T>((Class<T>) criteria.getEntityClass()),
					boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T querySingleResult(T entity) {
		BoundSql boundSql = SqlAssembleUtils.buildQuerySql(entity, null,
				this.getNameHandler());

		try {
			return runner.query(DataSourceUtils.getConnection(), boundSql
					.getSql(),
					new BeanHandler<T>((Class<T>) entity.getClass()), boundSql
							.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T querySingleResult(Criteria criteria) {
		BoundSql boundSql = SqlAssembleUtils.buildQuerySql(null, criteria,
				this.getNameHandler());
		try {
			return runner.query(DataSourceUtils.getConnection(),
					boundSql.getSql(),
					new BeanHandler<T>((Class<T>) criteria.getEntityClass()),
					boundSql.getParams().toArray());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public byte[] getBlobValue(Class<?> clazz, String fieldName, Long id) {
		String tableName = nameHandler.getTableName(clazz);
		String primaryName = nameHandler.getPKName(clazz);
		String columnName = nameHandler.getColumnName(clazz, fieldName);
		String tmp_sql = "select t.%s from %s t where t.%s = ?";
		String sql = String.format(tmp_sql, columnName, tableName, primaryName);
		try {
			return runner.query(DataSourceUtils.getConnection(), sql,
					new ScalarHandler<byte[]>(), new Object[] { id });
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public int updateForObject(final String sql, final Object[] args) {
		try {
			return runner.update(DataSourceUtils.getConnection(), sql, args);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	public <T> T queryForObject(final String sql, final Object[] args,
			final Class<T> mappedClass) {
		try {
			return runner.query(DataSourceUtils.getConnection(), sql,
					new BeanHandler<T>(mappedClass), args);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public Long addForObject(final String sql, final Object[] args) {
		try {
			return runner.insert(DataSourceUtils.getConnection(), sql,
					new ScalarHandler<Long>(), args);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public <T> List<T> queryForObjectList(final String sql,
			final Object[] args, final Class<T> clazz) {
		try {
			return runner.query(DataSourceUtils.getConnection(), sql,
					new BeanListHandler<T>(clazz), args);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public <T> Pager pageSearch(final String sql, final String countSql,
			Pager pager, final Object[] args, final Class<T> clazz) {
		if (pager == null) {
			logger.error("Pager Cann't be NULL! ");
			return null;
		}
		List<T> data = queryForObjectList(sql, args, clazz);
		pager.setList(data);
		Long count = this.queryForSimpleObject(countSql, args, Long.class);
		pager.setItemsTotal(count);
		return pager;
	}

	/**
	 * 获取名称处理器
	 *
	 * @return
	 */
	protected NameHandler getNameHandler() {

		if (this.nameHandler == null) {
			this.nameHandler = new DefaultNameHandler();
		}
		return this.nameHandler;
	}

	public void setNameHandler(NameHandler nameHandler) {
		this.nameHandler = nameHandler;
	}

	@Override
	public <T> T queryForSimpleObject(String sql, Object[] args,
			Class<T> mappedClass) {
		try {
			return runner.query(DataSourceUtils.getConnection(), sql,
					new ScalarHandler<T>(), args);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public <T> List<T> queryForSimpleObjectList(String sql, Object[] args,
			final Class<T> mappedClass) {
		try {
			return runner.query(DataSourceUtils.getConnection(), sql,
					new ColumnListHandler<T>(), args);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
