package com.haozileung.web.service.permission

import com.haozileung.infra.pager.PageResult
import com.haozileung.web.domain.permission.Menu

interface IMenuService {

    fun save(dictionary: Menu)

    fun update(dictionary: Menu)

    fun delete(dictionary: Menu)

    fun query(dictionary: Menu, pageNo: Int, pageSize: Int): PageResult<Menu>
}
