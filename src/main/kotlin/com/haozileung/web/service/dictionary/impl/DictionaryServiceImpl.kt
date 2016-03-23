package com.haozileung.web.service.dictionary.impl

import com.google.common.base.Strings
import com.haozileung.infra.page.PageResult
import com.haozileung.web.domain.dictionary.Dictionary
import com.haozileung.web.service.dictionary.IDictionaryService
import org.apache.commons.dbutils.QueryRunner
import org.apache.commons.dbutils.handlers.BeanListHandler
import org.apache.commons.dbutils.handlers.ScalarHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException
import java.util.*
import javax.inject.Inject

/**
 * Created by Haozi on 2016/3/22.
 */
class DictionaryServiceImpl : IDictionaryService {

    val logger: Logger = LoggerFactory.getLogger(IDictionaryService::class.java);
    @Inject
    lateinit var runner: QueryRunner;

    override fun save(dictionary: Dictionary): Long {
        val sql = "INSERT INTO sys_dictionary (dicTypeId,code,name,remarks,status)values(?,?,?,?,?,?,?)";
        try {
            return runner.insert(sql, ScalarHandler<Long>(), dictionary.dicTypeId, dictionary.code, dictionary.name, dictionary.remarks, dictionary.status);
        } catch (e: SQLException) {
            logger.error(e.message);
            return 0L;
        }
    }

    override fun update(dictionary: Dictionary): Int {
        var sql = "UPDATE sys_dictionary SET dicTypeId = ?, code = ?, name = ?, remarks = ?,status = ? WHERE dictionaryId = ?";
        try {
            return runner.update(sql, ScalarHandler<Integer>(), dictionary.dictionaryId, dictionary.code, dictionary.name, dictionary.remarks, dictionary.status, dictionary.dictionaryId);
        } catch (e: SQLException) {
            logger.error(e.message);
            return 0;
        }
    }

    override fun delete(dictionary: Dictionary): Int {
        var sql = "DELETE FROM sys_dictionary WHERE dictionaryId = ?";
        try {
            return runner.update(sql, ScalarHandler<Integer>(), dictionary.dictionaryId);
        } catch (e: SQLException) {
            logger.error(e.message);
            return 0;
        }
    }

    override fun query(dictionary: Dictionary, offset: Int, limit: Int): PageResult<Dictionary> {
        val pager = PageResult<Dictionary>();
        val querySQL = StringBuffer("SELECT * FROM sys_dictionary WHERE 1=1 ");
        val countSQL = StringBuffer("SELECT COUNT(*) FROM sys_dictionary WHERE 1=1 ");
        val where = StringBuilder();
        val params = ArrayList<Any>()
        if (dictionary.dictionaryId != null) {
            where.append("AND dictionaryId = ?");
            params.add(dictionary.dictionaryId as Long);
        }
        if (dictionary.dicTypeId != null) {
            where.append("AND dicTypeId = ?");
            params.add(dictionary.dicTypeId as Long);
        }
        if (!Strings.isNullOrEmpty(dictionary.code)) {
            where.append("AND code = ?");
            params.add(dictionary.code!!);
        }
        if (!Strings.isNullOrEmpty(dictionary.name)) {
            where.append("AND name = ?");
            params.add(dictionary.name!!);
        }
        if (dictionary.status != null) {
            where.append("AND status = ?");
            params.add(dictionary.status as Int);
        }
        try {
            var total = 0L;
            if (limit > 0) {
                querySQL.append(where).append(" LIMIT ").append(offset).append(",").append(limit);
                countSQL.append(where);
                try {
                    total = runner.query(countSQL.toString(), ScalarHandler<Long>(), *params.toArray());
                } catch (e: SQLException) {
                    logger.error(e.message);
                }
            }
            val list = runner.query(querySQL.toString(), BeanListHandler<Dictionary>(Dictionary::class.java), *params.toArray());
            pager.rows = list;
            pager.total = total;
        } catch (e: SQLException) {
            logger.error(e.message);
        } finally {
            return pager;
        }
    }
}
