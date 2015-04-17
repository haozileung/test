package com.haozileung.test.domain.system;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author Haozi
 */
public class UserRole implements Serializable {
    public static final String TABLE = "sys_user_role";
    /**
     *
     */
    private static final long serialVersionUID = -2201466479697675334L;
    private Long id;
    private Long roleId;
    private Long userId;

    public UserRole() {
    }

    public UserRole(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserRole == false)
            return false;
        if (this == obj)
            return true;
        UserRole other = (UserRole) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long value) {
        this.roleId = value;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId()).append("UserId", getUserId())
                .append("RoleId", getRoleId()).toString();
    }
}
