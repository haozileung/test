/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.haozileung.web.domain.system;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.haozileung.infra.dao.annotation.Column;
import com.haozileung.infra.dao.annotation.Table;
import com.haozileung.infra.utils.MD5Util;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */

@Table("t_user")
public class User implements java.io.Serializable {


    //columns START
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("status")
    private Integer status;
    //columns END


    public User() {
    }

    public User(Long id) {
        this.id = id;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        if (!Strings.isNullOrEmpty(value)) {
            Optional<String> password = MD5Util.MD5(value);
            if (password.isPresent()) {
                this.password = password.get();
            }
        }
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
        if (obj instanceof User == false) return false;
        if (this == obj) return true;
        User other = (User) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

