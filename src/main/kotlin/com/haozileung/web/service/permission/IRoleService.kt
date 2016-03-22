package com.haozileung.web.service.permission


import com.haozileung.infra.pager.PageResult
import com.haozileung.web.domain.permission.Role


interface IRoleService {

    fun save(dictionary: Role)

    fun update(dictionary: Role)

    fun delete(dictionary: Role)

    fun query(dictionary: Role, pageNo: Int, pageSize: Int): PageResult<Role>
}
