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
public class Resource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4180358046986107809L;
	public static final String TABLE = "sys_resource";
	private String code;
	private Long groupId;
	private Long id;
	private String name;
	private Integer status;
	private String type;
	private String url;

	public Resource() {
	}

	public Resource(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Resource == false)
			return false;
		if (this == obj)
			return true;
		Resource other = (Resource) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public String getCode() {
		return this.code;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getType() {
		return this.type;
	}

	public String getUrl() {
		return this.url;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public void setCode(String value) {
		this.code = value;
	}

	public void setGroupId(Long value) {
		this.groupId = value;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public void setType(String value) {
		this.type = value;
	}

	public void setUrl(String value) {
		this.url = value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("Code", getCode())
				.append("Name", getName()).append("Url", getUrl())
				.append("Type", getType()).append("GroupId", getGroupId())
				.append("Status", getStatus()).toString();
	}
}
