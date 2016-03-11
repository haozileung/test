package com.haozileung.web.service.permission;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.permission.Permission;


public interface IPermissionService {

    void save(Permission dictionary);

    void update(Permission dictionary);

    void delete(Permission dictionary);

    PageResult<Permission> query(Permission dictionary, int pageNo, int pageSize);
}
