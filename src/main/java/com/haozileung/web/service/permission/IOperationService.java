package com.haozileung.web.service.permission;

import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.permission.Operation;


public interface IOperationService {

    void save(Operation dictionary);

    void update(Operation dictionary);

    void delete(Operation dictionary);

    PageResult<Operation> query(Operation dictionary, int pageNo, int pageSize);
}
