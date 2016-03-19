package com.haozileung.infra.pager;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haozi on 16-3-9.
 */
public class PageResult<T> implements Serializable {
    private Long total = 0L;
    private List<T> rows = Lists.newArrayList();

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
