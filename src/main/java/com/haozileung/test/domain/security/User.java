package com.haozileung.test.domain.security;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3886396658069093652L;

	private String email;

	private Long id;

	private String password;

	private Status status;

	private String username;

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public Status getStatus() {
		return status;
	}

	public String getUsername() {
		return username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
