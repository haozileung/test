package com.haozileung.web.service.logging

import com.haozileung.infra.pager.PageResult
import com.haozileung.web.domain.logging.LoginLog

interface ILoginLogService {

    fun save(dictionary: LoginLog)

    fun update(dictionary: LoginLog)

    fun delete(dictionary: LoginLog)

    fun query(dictionary: LoginLog, pageNo: Int, pageSize: Int): PageResult<LoginLog>
}
