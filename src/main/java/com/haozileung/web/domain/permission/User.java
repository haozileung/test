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

public class User implements Serializable {

    // columns START
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String remarks;
    private Integer status;
    // columns END

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
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
        return new HashCodeBuilder().append(getUserId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof User == false)
            return false;
        if (this == obj)
            return true;
        User other = (User) obj;
        return new EqualsBuilder().append(getUserId(), other.getUserId()).isEquals();
    }
}
