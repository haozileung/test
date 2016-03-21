package com.haozileung.web.service.permission;


import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.permission.Role;


public interface IRoleService {

    void save(Role dictionary);

    void update(Role dictionary);

    void delete(Role dictionary);

    PageResult<Role> query(Role dictionary, int pageNo, int pageSize);
}
