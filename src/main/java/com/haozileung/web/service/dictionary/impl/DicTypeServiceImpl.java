package com.haozileung.web.service.dictionary.impl;

import com.google.inject.Inject;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.DicType;
import com.haozileung.web.service.dictionary.IDicTypeService;
import org.apache.commons.dbutils.QueryRunner;

public class DicTypeServiceImpl implements IDicTypeService {
    @Inject
    private QueryRunner runner;

    @Override
    public void save(DicType dictionary) {
    }

    @Override
    public void update(DicType dictionary) {
    }

    @Override
    public void delete(DicType dictionary) {
    }

    @Override
    public PageResult<DicType> query(DicType dictionary, int pageNo, int pageSize) {
        PageResult<DicType> pager = new PageResult<DicType>();
        return pager;
    }
}
