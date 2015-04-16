package com.haozileung.test.domain.system;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author Haozi
 *
 */
public class UserRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2201466479697675334L;
	public static final String TABLE = "sys_user_role";
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

	public Long getRoleId() {
		return this.roleId;
	}

	public Long getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public void setId(Long value) {
		this.id = value;
	}

	public void setRoleId(Long value) {
		this.roleId = value;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("UserId", getUserId())
				.append("RoleId", getRoleId()).toString();
	}
}
