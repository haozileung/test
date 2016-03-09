package com.haozileung.web.service.permission;

import com.google.inject.ImplementedBy;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.Dictionary;
import com.haozileung.web.service.dictionary.impl.DictionaryServiceImpl;

@ImplementedBy(DictionaryServiceImpl.class)
public interface IOperationService {

    void save(Dictionary dictionary);

    void update(Dictionary dictionary);

    void delete(Dictionary dictionary);

    PageResult<Dictionary> query(Dictionary dictionary, int pageNo, int pageSize);
}
