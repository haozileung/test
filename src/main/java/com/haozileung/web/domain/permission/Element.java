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
public class Element implements Serializable {

    // columns START
    private Long elementId;
    private String code;
    private String name;
    private String remarks;
    private Integer status;
    // columns END

    public Element() {
    }

    public Element(Long elementId) {
        this.elementId = elementId;
    }

    public Long getElementId() {
        return this.elementId;
    }

    public void setElementId(Long value) {
        this.elementId = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
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
        return new HashCodeBuilder().append(getElementId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Element == false)
            return false;
        if (this == obj)
            return true;
        Element other = (Element) obj;
        return new EqualsBuilder().append(getElementId(), other.getElementId()).isEquals();
    }
}
