package com.haozileung.test.domain.system;

import java.io.Serializable;

public class Resource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 460598377189334470L;
	private Long groupId;
	private Long id;
	private String name;
	private String permissionCode;
	private Integer type;
	private String url;

	public Long getGroupId() {
		return groupId;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public Integer getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
