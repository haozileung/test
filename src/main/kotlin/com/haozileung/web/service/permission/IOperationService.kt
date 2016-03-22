package com.haozileung.web.service.permission

import com.haozileung.infra.pager.PageResult
import com.haozileung.web.domain.permission.Operation


interface IOperationService {

    fun save(dictionary: Operation)

    fun update(dictionary: Operation)

    fun delete(dictionary: Operation)

    fun query(dictionary: Operation, pageNo: Int, pageSize: Int): PageResult<Operation>
}
