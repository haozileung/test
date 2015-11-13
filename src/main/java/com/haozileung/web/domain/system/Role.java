/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.haozileung.web.domain.system;


import com.haozileung.infra.dao.annotation.Column;
import com.haozileung.infra.dao.annotation.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */

@Table("t_role")
public class Role implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 7629368605599262756L;
	//columns START
    @Column("id")
    private Long id;
    @Column("code")
    private String code;
    @Column("name")
    private String name;
    @Column("status")
    private Integer status;
    //columns END


    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Role == false) return false;
        if (this == obj) return true;
        Role other = (Role) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

