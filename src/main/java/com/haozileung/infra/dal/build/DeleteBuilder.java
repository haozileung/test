package com.haozileung.infra.dal.build;

import com.haozileung.infra.dal.BoundSql;
import com.haozileung.infra.dal.handler.NameHandler;

/**
 * Created by liyd on 2015-12-7.
 */
public class DeleteBuilder extends AbstractSqlBuilder {

    protected static final String COMMAND_OPEN = "DELETE FROM ";

    /**
     * whereBuilder
     */
    private SqlBuilder            whereBuilder;

    public DeleteBuilder() {
        whereBuilder = new WhereBuilder();
    }

    public void addField(String fieldName, String sqlOperator, String fieldOperator, AutoFieldType type, Object value) {
        this.addCondition(fieldName, sqlOperator, fieldOperator, type, value);
    }

    public void addCondition(String fieldName, String sqlOperator, String fieldOperator, AutoFieldType type,
                             Object value) {
        whereBuilder.addCondition(fieldName, sqlOperator, fieldOperator, type, value);
    }

    public BoundSql build(Class<?> clazz, Object entity, boolean isIgnoreNull, NameHandler nameHandler) {
        super.mergeEntityFields(entity, AutoFieldType.WHERE, nameHandler, isIgnoreNull);
        whereBuilder.setTableAlias(getTableAlias());
        whereBuilder.getFields().putAll(this.getFields());
        String tableName = nameHandler.getTableName(clazz);
        StringBuilder sb = new StringBuilder(COMMAND_OPEN);
        sb.append(applyTableAlias(tableName)).append(" ");
        BoundSql boundSql = whereBuilder.build(clazz, entity, isIgnoreNull, nameHandler);
        sb.append(boundSql.getSql());
        return new CriteriaBoundSql(sb.toString(), boundSql.getParameters());
    }
}
