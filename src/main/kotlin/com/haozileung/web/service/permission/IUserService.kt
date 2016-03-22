package com.haozileung.web.service.permission

import com.haozileung.infra.pager.PageResult
import com.haozileung.web.domain.permission.User

interface IUserService {

    fun save(dictionary: User)

    fun update(dictionary: User)

    fun delete(dictionary: User)

    fun query(dictionary: User, pageNo: Int, pageSize: Int): PageResult<User>
}
