package com.haozileung.infra.dal;

import com.haozileung.infra.dal.build.CriteriaBoundSql;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yamcha on 2015-12-9.
 */
public class SimpleSqlFactory implements SqlFactory {

    @Override
    public BoundSql getBoundSql(String refSql, String expectParamKey, Object[] parameters) {
        return new CriteriaBoundSql(refSql, parameters == null ? new ArrayList<Object>() : Arrays.asList(parameters));
    }
}
