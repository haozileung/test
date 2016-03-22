package com.haozileung.web.service.dictionary;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.DicType;


interface IDicTypeService {

    fun save(dic: DicType): Long;

    fun update(dic: DicType): Int;

    fun delete(dic: DicType): Int;

    fun query(dic: DicType, offset: Int = 0, limit: Int = 10): PageResult<DicType>;
}