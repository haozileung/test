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
public class PermissionElement implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    private Long permissionId;
    private Long elementId;

    public PermissionElement() {
    }

    public PermissionElement(Long permissionId, Long elementId) {
        this.permissionId = permissionId;
        this.elementId = elementId;
    }

    public Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(Long value) {
        this.permissionId = value;
    }

    public Long getElementId() {
        return this.elementId;
    }

    public void setElementId(Long value) {
        this.elementId = value;
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