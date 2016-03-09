package com.haozileung.infra.pager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haozi on 16-3-9.
 */
public class PageResult<T> implements Serializable {
    private Long total;
    private List<T> rows;

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
