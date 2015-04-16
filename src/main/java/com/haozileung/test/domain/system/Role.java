package com.haozileung.test.domain.system;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.haozileung.test.domain.system.repository.RoleRepository;

/**
 * 
 * @author Haozi
 *
 */
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8127720436707168759L;
	public static final String TABLE = "sys_role";
	private Long id;
	private String name;
	private Boolean status;

	public void setPermission(List<Long> permissionIds) {
		RoleRepository.getInstance().setPermission(id, permissionIds);
	}

	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role == false)
			return false;
		if (this == obj)
			return true;
		Role other = (Role) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Boolean getStatus() {
		return this.status;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public void setId(Long value) {
		this.id = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setStatus(Boolean value) {
		this.status = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("Name", getName())
				.append("Status", getStatus()).toString();
	}
}
