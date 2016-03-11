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

import java.io.Serializable;

/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */
public class PermissionMenu implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    private Long permissionId;
    private Long menuId;

    public PermissionMenu() {
    }

    public PermissionMenu(Long permissionId, Long menuId) {
        this.permissionId = permissionId;
        this.menuId = menuId;
    }

    public Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(Long value) {
        this.permissionId = value;
    }

    public Long getMenuId() {
        return this.menuId;
    }

    public void setMenuId(Long value) {
        this.menuId = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }
}