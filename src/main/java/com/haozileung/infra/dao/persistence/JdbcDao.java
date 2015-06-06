package com.haozileung.infra.dao.persistence;

import java.util.List;

import com.haozileung.infra.dao.pager.Pager;

/**
 * jdbc操作dao
 */
public interface JdbcDao {

	/**
	 * 插入一条记录 自动处理主键
	 *
	 * @param entity
	 * @return
	 */
	Long insert(Object entity);

	/**
	 * 插入一条记录 自动处理主键
	 *
	 * @param criteria
	 *            the criteria
	 * @return long long
	 */
	Long insert(Criteria criteria);

	/**
	 * 保存一条记录，不处理主键
	 *
	 * @param entity
	 */
	void save(Object entity);

	/**
	 * 保存一条记录，不处理主键
	 *
	 * @param criteria
	 *            the criteria
	 */
	void save(Criteria criteria);

	/**
	 * 根据Criteria更新
	 *
	 * @param criteria
	 *            the criteria
	 */
	void update(Criteria criteria);

	/**
	 * 根据实体更新
	 *
	 * @param entity
	 *            the entity
	 */
	void update(Object entity);

	/**
	 * 根据Criteria删除
	 *
	 * @param criteria
	 *            the criteria
	 */
	void delete(Criteria criteria);

	/**
	 * 删除记录 此方法会以实体中不为空的字段为条件
	 *
	 * @param entity
	 */
	void delete(Object entity);

	/**
	 * 删除记录
	 *
	 * @param clazz
	 *            the clazz
	 * @param id
	 *            the id
	 */
	void delete(Class<?> clazz, Long id);

	/**
	 * 删除所有记录(TRUNCATE ddl权限)
	 *
	 * @param clazz
	 *            the clazz
	 */
	void deleteAll(Class<?> clazz);

	/**
	 * 按设置的条件查询
	 *
	 * @param <T>
	 *            the type parameter
	 * @param criteria
	 *            the criteria
	 * @return list
	 */
	<T> List<T> queryList(Criteria criteria);

	/**
	 * 查询列表
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	<T> List<T> queryList(T entity);

	/**
	 * 查询列表
	 *
	 * @param <T>
	 *            the type parameter
	 * @param entity
	 *            the entity
	 * @param criteria
	 *            the criteria
	 * @return the list
	 */
	<T> List<T> queryList(T entity, Criteria criteria);

	/**
	 * 查询记录数
	 *
	 * @param entity
	 * @return
	 */
	int queryCount(Object entity);

	/**
	 * 查询记录数
	 *
	 * @param criteria
	 *            the criteria
	 * @return int int
	 */
	int queryCount(Criteria criteria);

	/**
	 * 查询记录数
	 *
	 * @param entity
	 *            the entity
	 * @param criteria
	 *            the criteria
	 * @return int int
	 */
	int queryCount(Object entity, Criteria criteria);

	/**
	 * 根据主键得到记录
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @param id
	 *            the id
	 * @return t
	 */
	<T> T get(Class<T> clazz, Long id);

	/**
	 * 根据主键得到记录
	 *
	 * @param <T>
	 *            the type parameter
	 * @param criteria
	 *            the criteria
	 * @param id
	 *            the id
	 * @return t
	 */
	<T> T get(Criteria criteria, Long id);

	/**
	 * 查询单个记录
	 *
	 * @param <T>
	 *            the type parameter
	 * @param entity
	 *            the entity
	 * @return t t
	 */
	<T> T querySingleResult(T entity);

	/**
	 * 查询单个记录
	 *
	 * @param <T>
	 *            the type parameter
	 * @param criteria
	 *            the criteria
	 * @return t t
	 */
	<T> T querySingleResult(Criteria criteria);

	/**
	 * 查询blob字段值
	 *
	 * @param clazz
	 * @param fieldName
	 * @param id
	 * @return
	 */
	byte[] getBlobValue(Class<?> clazz, String fieldName, Long id);

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	int updateForObject(final String sql, final Object[] args);

	/**
	 * 
	 * @param sql
	 * @param args
	 * @param mappedClass
	 * @return
	 */
	<T> T queryForObject(final String sql, final Object[] args,
			final Class<T> mappedClass);

	/**
	 * 
	 * @param sql
	 * @param args
	 * @param mappedClass
	 * @return
	 */
	<T> T queryForSimpleObject(final String sql, final Object[] args,
			final Class<T> mappedClass);

	/**
	 * 
	 * @param sql
	 * @param args
	 * @param mappedClass
	 * @return
	 */
	<T> List<T> queryForSimpleObjectList(String sql, Object[] args,
			final Class<T> mappedClass);

	/**
	 * 
	 * @param sql
	 * @param args
	 * @param clazz
	 * @return
	 */
	<T> List<T> queryForObjectList(final String sql, final Object[] args,
			final Class<T> clazz);

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	Long addForObject(final String sql, final Object[] args);

	<T> Pager pageSearch(final String sql, final String countSql, Pager pager,
			final Object[] args, final Class<T> clazz);
}
