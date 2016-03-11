/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.haozileung.web.domain.permission;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */

public class Operation implements Serializable {

    // columns START
    private Long operationId;
    private String url;
    private String name;
    private String remarks;
    private Integer status;
    // columns END

    public Operation() {
    }

    public Operation(Long operationId) {
        this.operationId = operationId;
    }

    public Long getOperationId() {
        return this.operationId;
    }

    public void setOperationId(Long value) {
        this.operationId = value;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String value) {
        this.remarks = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getOperationId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Operation == false)
            return false;
        if (this == obj)
            return true;
        Operation other = (Operation) obj;
        return new EqualsBuilder().append(getOperationId(), other.getOperationId()).isEquals();
    }
}
