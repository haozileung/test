package com.haozileung.web.service.dictionary;

import com.google.inject.ImplementedBy
import com.haozileung.infra.page.PageResult
import com.haozileung.web.domain.dictionary.Dictionary
import com.haozileung.web.service.dictionary.impl.DictionaryServiceImpl

@ImplementedBy(DictionaryServiceImpl::class)
interface IDictionaryService {
    fun save(dictionary: Dictionary): Long;

    fun update(dictionary: Dictionary): Int;

    fun delete(dictionary: Dictionary): Int;

    fun query(dictionary: Dictionary, offset: Int = 0, limit: Int = 10): PageResult<Dictionary>;
}
