package com.haozileung.infra.dal.handler;

/**
 * 不作任务特殊处理的nameHandler。解析{}符号
 * 
 * Created by yamcha on 2015-12-9.
 */
public class NoneNameHandler implements NameHandler {

	@Override
	public String getTableName(Class<?> entityClass) {
		return entityClass.getSimpleName();
	}

	@Override
	public String getPkFieldName(Class<?> entityClass) {
		return entityClass.getSimpleName() + "Id";
	}

	@Override
	public String getPkColumnName(Class<?> entityClass) {
		return entityClass.getSimpleName() + "Id";
	}

	@Override
	public String getColumnName(Class<?> entityClass, String fieldName) {
		return fieldName;
	}

	@Override
	public String getPkNativeValue(Class<?> entityClass, String dialect) {
		return null;
	}
}
