package com.haozileung.web.domain.dictionary;

import com.haozileung.infra.dal.annotation.ID;
import com.haozileung.infra.dal.annotation.Table;

/**
 * Created by haozi on 16-3-7.
 */
@Table("sys_dictionary")
public class Dictionary {
    @ID
    private Long dictionaryId;
    private Long dicTypeId;
    private String code;
    private String name;
    private String remarks;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(Long dicTypeId) {
        this.dicTypeId = dicTypeId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
