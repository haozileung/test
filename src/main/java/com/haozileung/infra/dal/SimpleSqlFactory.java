package com.haozileung.infra.dal;

import java.util.ArrayList;
import java.util.Arrays;

import com.haozileung.infra.dal.build.CriteriaBoundSql;

/**
 * Created by yamcha on 2015-12-9.
 */
public class SimpleSqlFactory implements SqlFactory {

    public BoundSql getBoundSql(String refSql, String expectParamKey, Object[] parameters) {
        return new CriteriaBoundSql(refSql, parameters == null ? new ArrayList<Object>() : Arrays.asList(parameters));
    }
}
