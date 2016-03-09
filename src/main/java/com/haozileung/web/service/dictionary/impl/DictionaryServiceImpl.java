package com.haozileung.web.service.dictionary.impl;

import com.google.inject.Inject;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.Dictionary;
import com.haozileung.web.service.dictionary.IDictionaryService;

public class DictionaryServiceImpl implements IDictionaryService {
    @Inject
    private JdbcDao dao;

    @Override
    public void save(Dictionary dictionary) {
        dao.save(dao);
    }

    @Override
    public void update(Dictionary dictionary) {
        dao.update(dictionary);
    }

    @Override
    public void delete(Dictionary dictionary) {
        dao.delete(dictionary);
    }

    @Override
    public PageResult<Dictionary> query(Dictionary dictionary, int pageNo, int pageSize) {
        PageResult<Dictionary> pager = new PageResult<Dictionary>();
        pager.setRows(dao.queryList(dictionary));
        pager.setTotal(Long.valueOf(dao.queryCount(dictionary)));
        return pager;
    }
}
