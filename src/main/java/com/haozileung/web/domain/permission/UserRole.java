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
public class UserRole implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    private Long userId;
    private Long roleId;

    public UserRole() {
    }

    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long value) {
        this.roleId = value;
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