package com.haozileung.web.service.dictionary.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.DicType;
import com.haozileung.web.service.dictionary.IDicTypeService;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class DicTypeServiceImpl implements IDicTypeService {
    private static final Logger logger = LoggerFactory.getLogger(DicTypeServiceImpl.class);
    @Inject
    private QueryRunner runner;

    @Override
    public Long save(DicType dicType) {
        String sql = "INSERT INTO sys_dic_type (parentId,name,remarks,status)values(?,?,?,?)";
        try {
            return runner.insert(sql, new ScalarHandler<Long>(), dicType.getParentId(), dicType.getName(), dicType.getRemarks(), dicType.getStatus());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return 0l;
        }
    }

    @Override
    public Integer update(DicType dicType) {
        String sql = "UPDATE sys_dic_type SET parentId = ?, name = ?, remarks = ?,status = ? WHERE dicTypeId = ?";
        try {
            return runner.update(sql, new ScalarHandler<Integer>(), dicType.getParentId(), dicType.getName(), dicType.getRemarks(), dicType.getStatus(), dicType.getDicTypeId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return 0;
        }
    }

    @Override
    public Integer delete(DicType dicType) {
        String sql = "DELETE FROM sys_dic_type WHERE dicTypeId = ?";
        try {
            return runner.update(sql, new ScalarHandler<Integer>(), dicType.getDicTypeId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            return 0;
        }
    }

    @Override
    public PageResult<DicType> query(DicType dicType, int pageNo, int pageSize) {
        PageResult<DicType> pager = new PageResult<DicType>();
        StringBuffer querySQL = new StringBuffer("SELECT * FROM sys_dic_type WHERE 1=1 ");
        StringBuffer countSQL = new StringBuffer("SELECT COUNT(*) FROM sys_dic_type WHERE 1=1 ");
        StringBuilder where = new StringBuilder();
        List<Object> params = Lists.newArrayList();
        if (dicType.getParentId() != null) {
            where.append("AND parentId = ?");
            params.add(dicType.getParentId());
        }
        if (dicType.getDicTypeId() != null) {
            where.append("AND dicTypeId = ?");
            params.add(dicType.getDicTypeId());
        }
        if (dicType.getName() != null) {
            where.append("AND name = ?");
            params.add(dicType.getName());
        }
        if (dicType.getStatus() != null) {
            where.append("AND status = ?");
            params.add(dicType.getStatus());
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize > 0) {
            querySQL.append(where).append(" LIMIT ").append((pageNo - 1) * pageSize).append(",").append(pageSize);
        }
        countSQL.append(where);
        try {
            List<DicType> list = runner.query(querySQL.toString(), new BeanListHandler<DicType>(DicType.class), params.toArray());
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
