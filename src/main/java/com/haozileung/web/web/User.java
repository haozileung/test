package com.haozileung.web.web;

import java.io.Serializable;

import com.haozileung.infra.dao.annotation.Table;

@Table("user")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4093166956475264449L;
	private String name;
	private Integer id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User() {
	}

	public User(String name, Integer id) {
		super();
		this.name = name;
		this.id = id;
	}

}
