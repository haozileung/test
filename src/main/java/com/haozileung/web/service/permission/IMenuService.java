package com.haozileung.web.service.permission;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.permission.Menu;

public interface IMenuService {

    void save(Menu dictionary);

    void update(Menu dictionary);

    void delete(Menu dictionary);

    PageResult<Menu> query(Menu dictionary, int pageNo, int pageSize);
}
