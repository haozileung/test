package com.haozileung.web.service.dictionary;

import com.google.inject.ImplementedBy;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.DicType;
import com.haozileung.web.service.dictionary.impl.DicTypeServiceImpl;

@ImplementedBy(DicTypeServiceImpl.class)
public interface IDicTypeService {

    void save(DicType dictionary);

    void update(DicType dictionary);

    void delete(DicType dictionary);

    PageResult<DicType> query(DicType dictionary, int pageNo, int pageSize);
}
