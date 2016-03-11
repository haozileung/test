package com.haozileung.web.service.permission;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.permission.User;

public interface IUserService {

    void save(User dictionary);

    void update(User dictionary);

    void delete(User dictionary);

    PageResult<User> query(User dictionary, int pageNo, int pageSize);
}
