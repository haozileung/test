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

@Table("t_user_role")
public class UserRole implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -5583261588376609132L;
	//columns START
    @Column("id")
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("role_id")
    private Long roleId;
    //columns END


    public UserRole() {
    }

    public UserRole(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long value) {
        this.roleId = value;
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
        if (obj instanceof UserRole == false) return false;
        if (this == obj) return true;
        UserRole other = (UserRole) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

