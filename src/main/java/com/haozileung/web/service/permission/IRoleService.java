package com.haozileung.web.service.permission;

import com.google.inject.ImplementedBy;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.permission.Role;
import com.haozileung.web.service.dictionary.impl.DictionaryServiceImpl;

@ImplementedBy(DictionaryServiceImpl.class)
public interface IRoleService {

    void save(Role dictionary);

    void update(Role dictionary);

    void delete(Role dictionary);

    PageResult<Role> query(Role dictionary, int pageNo, int pageSize);
}
