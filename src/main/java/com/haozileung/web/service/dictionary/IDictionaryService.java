package com.haozileung.web.service.dictionary;

import com.google.inject.ImplementedBy;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.Dictionary;
import com.haozileung.web.service.dictionary.impl.DictionaryServiceImpl;

@ImplementedBy(DictionaryServiceImpl.class)
public interface IDictionaryService {

    Long save(Dictionary dictionary);

    Integer update(Dictionary dictionary);

    Integer delete(Dictionary dictionary);

    PageResult<Dictionary> query(Dictionary dictionary, int pageNo, int pageSize);
}
