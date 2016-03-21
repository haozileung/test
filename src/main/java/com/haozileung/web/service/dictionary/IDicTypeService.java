package com.haozileung.web.service.dictionary;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.DicType;


public interface IDicTypeService {

    Long save(DicType dic);

    Integer update(DicType dic);

    Integer delete(DicType dic);

    PageResult<DicType> query(DicType dic, int pageNo, int pageSize);
}