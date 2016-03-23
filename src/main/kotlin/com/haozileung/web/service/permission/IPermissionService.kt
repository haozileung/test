package com.haozileung.web.service.permission

import com.haozileung.infra.page.PageResult
import com.haozileung.web.domain.permission.Permission


interface IPermissionService {

    fun save(dictionary: Permission)

    fun update(dictionary: Permission)

    fun delete(dictionary: Permission)

    fun query(dictionary: Permission, pageNo: Int, pageSize: Int): PageResult<Permission>
}
