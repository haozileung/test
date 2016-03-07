package com.haozileung.infra.dal.build;

import com.haozileung.infra.dal.BoundSql;

import java.util.List;

/**
 * Created by yamcha on 2015-12-7.
 */
public class CriteriaBoundSql implements BoundSql {

    /**
     * sql
     */
    private String sql;

    /**
     * parameters
     */
    private List<Object> parameters;

    /**
     * Constructor
     *
     * @param sql
     * @param parameters
     */
    public CriteriaBoundSql(String sql, List<Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    @Override
    public String getSql() {
        return this.sql;
    }

    @Override
    public List<Object> getParameters() {
        return this.parameters;
    }
}
