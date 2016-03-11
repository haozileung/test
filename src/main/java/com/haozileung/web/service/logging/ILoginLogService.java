package com.haozileung.web.service.logging;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.logging.LoginLog;

public interface ILoginLogService {

    void save(LoginLog dictionary);

    void update(LoginLog dictionary);

    void delete(LoginLog dictionary);

    PageResult<LoginLog> query(LoginLog dictionary, int pageNo, int pageSize);
}
