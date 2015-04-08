package com.haozileung.test.domain.security;

import java.io.Serializable;

public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8177652945251421826L;
	private Long id;
	private String roleName;
	private Integer status;

	public Long getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
