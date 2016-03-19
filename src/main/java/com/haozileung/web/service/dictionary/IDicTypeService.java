package com.haozileung.web.service.dictionary;

import com.google.inject.ImplementedBy;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.DicType;
import com.haozileung.web.service.dictionary.impl.DicTypeServiceImpl;

@ImplementedBy(DicTypeServiceImpl.class)
public interface IDicTypeService {

    Long save(DicType dic);

    Integer update(DicType dic);

    Integer delete(DicType dic);

    PageResult<DicType> query(DicType dic, int pageNo, int pageSize);
}