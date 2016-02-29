package com.haozileung.infra.dal.build;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.haozileung.infra.dal.BoundSql;
import com.haozileung.infra.dal.exceptions.JdbcAssistantException;
import com.haozileung.infra.dal.handler.NameHandler;

/**
 * Created by yamcha on 2015-12-7.
 */
public class InsertBuilder extends AbstractSqlBuilder {

	protected static final String COMMAND_OPEN = "INSERT INTO ";

	@Override
	public void addField(String fieldName, String sqlOperator, String fieldOperator, AutoFieldType type, Object value) {
		AutoField autoField = buildAutoField(fieldName, sqlOperator, fieldOperator, type, value);
		this.autoFields.put(fieldName, autoField);
	}

	@Override
	public void addCondition(String fieldName, String sqlOperator, String fieldOperator, AutoFieldType type,
			Object value) {
		throw new JdbcAssistantException("InsertBuilder不支持设置条件");
	}

	@Override
	public BoundSql build(Class<?> clazz, Object entity, boolean isIgnoreNull, NameHandler nameHandler) {
		super.mergeEntityFields(entity, AutoFieldType.INSERT, nameHandler, isIgnoreNull);
		StringBuilder sql = new StringBuilder(COMMAND_OPEN);
		StringBuilder args = new StringBuilder("(");
		List<Object> params = new ArrayList<Object>();
		String tableName = nameHandler.getTableName(clazz);
		sql.append(tableName).append(" (");

		for (Map.Entry<String, AutoField> entry : getFields().entrySet()) {
			AutoField autoField = entry.getValue();
			// 忽略null值
			if (autoField.getValue() == null && isIgnoreNull) {
				continue;
			}
			// 原生类型
			if (autoField.isNativeField()) {
				String nativeFieldName = tokenParse(autoField.getName(), clazz, nameHandler);
				String nativeValue = tokenParse(String.valueOf(autoField.getValue()), clazz, nameHandler);
				sql.append(nativeFieldName).append(",");
				args.append(nativeValue);
			} else {
				String columnName = nameHandler.getColumnName(clazz, entry.getKey());
				sql.append(columnName).append(",");
				args.append("?");
				params.add(autoField.getValue());
			}
			args.append(",");

		}
		sql.deleteCharAt(sql.length() - 1);
		args.deleteCharAt(args.length() - 1);
		sql.append(")").append(" VALUES ").append(args.append(")"));
		return new CriteriaBoundSql(sql.toString(), params);
	}
}
