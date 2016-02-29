package com.haozileung.infra.dal;

/**
 * Created by yamcha on 2015-12-4.
 */
public interface SqlFactory {

	/**
	 * 获取BoundSql
	 *
	 * @param refSql
	 *            the ref sql
	 * @param expectParamKey
	 *            the expect param key
	 * @param parameters
	 *            the parameters
	 * @return bound sql
	 */
	BoundSql getBoundSql(String refSql, String expectParamKey, Object[] parameters);
}
