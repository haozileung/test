/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.haozileung.web.domain.blog;


import com.haozileung.infra.dao.annotation.Column;
import com.haozileung.infra.dao.annotation.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */

@Table("t_comment")
public class Comment implements java.io.Serializable {


    //columns START
    @Column("id")
    private Long id;
    @Column("postid")
    private Long postId;
    @Column("name")
    private String name;
    @Column("email")
    private String email;
    @Column("createtime")
    private Date createTime;
    @Column("message")
    private String message;
    //columns END


    public Comment() {
    }

    public Comment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long value) {
        this.postId = value;
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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String value) {
        this.message = value;
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
        if (obj instanceof Comment == false) return false;
        if (this == obj) return true;
        Comment other = (Comment) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

