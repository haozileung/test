package com.haozileung.test.domain.system;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author Haozi
 */
public class RoleResource implements Serializable {
	public static final String TABLE = "role_resource";
	/**
     *
     */
	private static final long serialVersionUID = -4306085774977636498L;
	private Long id;
	private Long resourceId;
	private Long roleId;

	// columns END

	public RoleResource() {
	}

	public RoleResource(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoleResource == false)
			return false;
		if (this == obj)
			return true;
		RoleResource other = (RoleResource) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Long value) {
		this.resourceId = value;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long value) {
		this.roleId = value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("RoleId", getRoleId())
				.append("ResourceId", getResourceId()).toString();
	}
}
