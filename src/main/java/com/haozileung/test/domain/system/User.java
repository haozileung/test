package com.haozileung.test.domain.system;

import com.haozileung.test.domain.system.repository.UserRepository;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @author Haozi
 */
public class User implements Serializable {
	public static final String TABLE = "user";
	/**
     *
     */
	private static final long serialVersionUID = -7862430991787586647L;
	private String email;
	private Long id;
	private String password;
	private Boolean status;
	private String name;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public void setRole(List<Long> roleIds) {
		UserRepository.getInstance().setRole(id, roleIds);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User == false)
			return false;
		if (this == obj)
			return true;
		User other = (User) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean value) {
		this.status = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("name", getName())
				.append("Email", getEmail()).append("Password", getPassword())
				.append("Status", getStatus()).toString();
	}
}
