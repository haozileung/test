package com.haozileung.web.service.logging

import com.haozileung.infra.pager.PageResult
import com.haozileung.web.domain.logging.OperateLog

interface IOperateLogService {

    fun save(dictionary: OperateLog)

    fun update(dictionary: OperateLog)

    fun delete(dictionary: OperateLog)

    fun query(dictionary: OperateLog, pageNo: Int, pageSize: Int): PageResult<OperateLog>
}
