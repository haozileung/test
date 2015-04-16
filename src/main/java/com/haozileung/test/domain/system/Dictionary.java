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
public class Dictionary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6620142014721990438L;

	public static final String TABLE = "sys_dictionary";
	private String code;
	private Long id;
	private String name;
	private Integer order;
	private Long parentId;
	private Integer stataus;

	public Dictionary() {
	}

	public Dictionary(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dictionary == false)
			return false;
		if (this == obj)
			return true;
		Dictionary other = (Dictionary) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public String getCode() {
		return this.code;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getOrder() {
		return this.order;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public Integer getStataus() {
		return this.stataus;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public void setCode(String value) {
		this.code = value;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setOrder(Integer value) {
		this.order = value;
	}

	public void setParentId(Long value) {
		this.parentId = value;
	}

	public void setStataus(Integer value) {
		this.stataus = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("Code", getCode())
				.append("Name", getName()).append("Order", getOrder())
				.append("ParentId", getParentId())
				.append("Stataus", getStataus()).toString();
	}
}
