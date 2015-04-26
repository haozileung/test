package com.haozileung.test.domain.system;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author Haozi
 */
public class Dictionary implements Serializable {

	public static final String TABLE = "dictionary";
	/**
     *
     */
	private static final long serialVersionUID = -6620142014721990438L;
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
		if (obj instanceof Dictionary) {
			if (this == obj)
				return true;
			Dictionary other = (Dictionary) obj;
			return new EqualsBuilder().append(getId(), other.getId())
					.isEquals();
		} else {
			return false;
		}
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer value) {
		this.order = value;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long value) {
		this.parentId = value;
	}

	public Integer getStataus() {
		return this.stataus;
	}

	public void setStataus(Integer value) {
		this.stataus = value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
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
