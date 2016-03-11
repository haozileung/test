package com.haozileung.web.service.logging;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.logging.OperateLog;

public interface IOperateLogService {

    void save(OperateLog dictionary);

    void update(OperateLog dictionary);

    void delete(OperateLog dictionary);

    PageResult<OperateLog> query(OperateLog dictionary, int pageNo, int pageSize);
}
