package com.haozileung.web.domain;

import java.io.Serializable;

public class Archive implements Serializable {

	private Long id;

	private Integer year;

	private Integer month;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
