package com.haozileung.web.domain.dictionary;

import com.haozileung.infra.dal.annotation.ID;
import com.haozileung.infra.dal.annotation.Table;

/**
 * Created by haozi on 16-3-8.
 */
@Table("sys_dic_type")
public class DicType {

    @ID
    private Long dicTypeId;

    private Long parentId;

    private String name;

    private String remarks;

    private Integer status;

    public Long getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(Long dicTypeId) {
        this.dicTypeId = dicTypeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
