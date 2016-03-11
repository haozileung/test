package com.haozileung.web.service.permission;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.permission.Element;

public interface IElementService {

    void save(Element dictionary);

    void update(Element dictionary);

    void delete(Element dictionary);

    PageResult<Element> query(Element dictionary, int pageNo, int pageSize);
}
