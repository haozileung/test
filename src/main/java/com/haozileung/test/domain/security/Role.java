package com.haozileung.test.domain.security;

public class Role {
	private Long id;
	private String roleName;
	private Status status;
	public Long getId() {
		return id;
	}
	public String getRoleName() {
		return roleName;
	}
	public Status getStatus() {
		return status;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
