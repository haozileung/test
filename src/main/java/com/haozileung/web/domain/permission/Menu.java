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
public class Menu implements Serializable {

    // columns START
    private Long menuId;
    private String url;
    private String name;
    private Long parentId;
    private String remarks;
    private Integer status;
    // columns END

    public Menu() {
    }

    public Menu(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Long value) {
        this.menuId = value;
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

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long value) {
        this.parentId = value;
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
        return new HashCodeBuilder().append(getMenuId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Menu == false)
            return false;
        if (this == obj)
            return true;
        Menu other = (Menu) obj;
        return new EqualsBuilder().append(getMenuId(), other.getMenuId()).isEquals();
    }
}
