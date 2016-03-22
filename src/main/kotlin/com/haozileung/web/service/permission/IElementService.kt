package com.haozileung.web.service.permission

import com.haozileung.infra.pager.PageResult
import com.haozileung.web.domain.permission.Element

interface IElementService {

    fun save(dictionary: Element)

    fun update(dictionary: Element)

    fun delete(dictionary: Element)

    fun query(dictionary: Element, pageNo: Int, pageSize: Int): PageResult<Element>
}
