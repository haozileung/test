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
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7862430991787586647L;
	public static final String TABLE = "sys_user";
	private String email;
	private Long id;
	private String password;
	private Boolean status;
	private String username;

	public User() {
	}

	public User(Long id) {
		this.id = id;
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

	public Long getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public void setStatus(Boolean value) {
		this.status = value;
	}

	public void setUsername(String value) {
		this.username = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("Username", getUsername())
				.append("Email", getEmail()).append("Password", getPassword())
				.append("Status", getStatus()).toString();
	}
}
