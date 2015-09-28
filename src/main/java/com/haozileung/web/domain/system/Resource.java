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

@Table("t_resource")
public class Resource implements java.io.Serializable {


    //columns START
    @Column("id")
    private Long id;
    @Column("code")
    private String code;
    @Column("name")
    private String name;
    @Column("icon")
    private String icon;
    @Column("url")
    private String url;
    @Column("order_no")
    private Integer orderNo;
    @Column("type")
    private String type;
    @Column("group_id")
    private Long groupId;
    @Column("status")
    private Integer status;
    //columns END


    public Resource() {
    }

    public Resource(Long id) {
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String value) {
        this.icon = value;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(Integer value) {
        this.orderNo = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long value) {
        this.groupId = value;
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
        if (obj instanceof Resource == false) return false;
        if (this == obj) return true;
        Resource other = (Resource) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

