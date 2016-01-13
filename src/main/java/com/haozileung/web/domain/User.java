package com.haozileung.web.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.haozileung.infra.dal.annotation.Column;
import com.haozileung.infra.dal.annotation.Table;

@Table("test")
public class User {

	private Integer age;
	private Integer id;
	private Integer status;
	@Column("name")
	private String userName;

	public Integer getAge() {
		return age;
	}

	public Integer getId() {
		return id;
	}

	public Integer getStatus() {
		return status;
	}

	public String getUserName() {
		return userName;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}
