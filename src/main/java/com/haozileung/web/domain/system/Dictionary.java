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

@Table("t_dictionary")
public class Dictionary implements java.io.Serializable {


    //columns START
    @Column("id")
    private Long id;
    @Column("code")
    private String code;
    @Column("value")
    private String value;
    @Column("order_no")
    private Integer orderNo;
    @Column("parent_id")
    private Long parentId;
    @Column("stataus")
    private Integer stataus;
    //columns END


    public Dictionary() {
    }

    public Dictionary(Long id) {
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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(Integer value) {
        this.orderNo = value;
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
        if (obj instanceof Dictionary == false) return false;
        if (this == obj) return true;
        Dictionary other = (Dictionary) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

