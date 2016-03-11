package com.haozileung.web.service.dictionary.impl;

import com.google.inject.Inject;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.Dictionary;
import com.haozileung.web.service.dictionary.IDictionaryService;
import org.apache.commons.dbutils.QueryRunner;

public class DictionaryServiceImpl implements IDictionaryService {
    @Inject
    private QueryRunner runner;

    @Override
    public void save(Dictionary dictionary) {
    }

    @Override
    public void update(Dictionary dictionary) {
    }

    @Override
    public void delete(Dictionary dictionary) {
    }

    @Override
    public PageResult<Dictionary> query(Dictionary dictionary, int pageNo, int pageSize) {
        PageResult<Dictionary> pager = new PageResult<Dictionary>();
        return pager;
    }
}
