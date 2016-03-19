package com.haozileung.web.service.dictionary.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.Dictionary;
import com.haozileung.web.service.dictionary.IDictionaryService;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class DictionaryServiceImpl implements IDictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);
    @Inject
    private QueryRunner runner;
    @Override
    public Long save(Dictionary dictionary) {
        String sql = "INSERT INTO sys_dictionary (dicTypeId,code,name,remarks,status)values(?,?,?,?,?,?,?)";
        try {
            return runner.insert(sql, new ScalarHandler<Long>(), dictionary.getDicTypeId(), dictionary.getCode(), dictionary.getName(), dictionary.getRemarks(), dictionary.getStatus());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return 0l;
        }
    }

    @Override
    public Integer update(Dictionary dictionary) {
        String sql = "UPDATE sys_dictionary SET dicTypeId = ?, code = ?, name = ?, remarks = ?,status = ? WHERE dictionaryId = ?";
        try {
            return runner.update(sql, new ScalarHandler<Integer>(), dictionary.getDictionaryId(), dictionary.getCode(), dictionary.getName(), dictionary.getRemarks(), dictionary.getStatus(), dictionary.getDictionaryId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return 0;
        }
    }

    @Override
    public Integer delete(Dictionary dictionary) {
        String sql = "DELETE FROM sys_dictionary WHERE dictionaryId = ?";
        try {
            return runner.update(sql, new ScalarHandler<Integer>(), dictionary.getDictionaryId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return 0;
        }
    }

    @Override
    public PageResult<Dictionary> query(Dictionary dictionary, int pageNo, int pageSize) {
        PageResult<Dictionary> pager = new PageResult<Dictionary>();
        StringBuffer querySQL = new StringBuffer("SELECT * FROM sys_dictionary WHERE 1=1 ");
        StringBuffer countSQL = new StringBuffer("SELECT COUNT(*) FROM sys_dictionary WHERE 1=1 ");
        StringBuilder where = new StringBuilder();
        List<Object> params = Lists.newArrayList();
        if (dictionary.getDictionaryId() != null) {
            where.append("AND dictionaryId = ?");
            params.add(dictionary.getDictionaryId());
        }
        if (dictionary.getDicTypeId() != null) {
            where.append("AND dicTypeId = ?");
            params.add(dictionary.getDicTypeId());
        }
        if (dictionary.getCode() != null) {
            where.append("AND code = ?");
            params.add(dictionary.getCode());
        }
        if (dictionary.getName() != null) {
            where.append("AND name = ?");
            params.add(dictionary.getName());
        }
        if (dictionary.getStatus() != null) {
            where.append("AND status = ?");
            params.add(dictionary.getStatus());
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize > 0) {
            querySQL.append(where).append(" LIMIT ").append((pageNo - 1) * pageSize).append(",").append(pageSize);
        }
        countSQL.append(where);
        try {
            List<Dictionary> list = runner.query(querySQL.toString(), new BeanListHandler<Dictionary>(Dictionary.class), params.toArray());
            Long total = runner.query(countSQL.toString(), new ScalarHandler<Long>(), params.toArray());
            pager.setRows(list);
            pager.setTotal(total);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return pager;
        }
    }
}
