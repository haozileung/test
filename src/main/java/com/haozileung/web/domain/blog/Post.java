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

@Table("t_post")
public class Post implements java.io.Serializable {


    //columns START
    @Column("id")
    private Long id;
    @Column("title")
    private String title;
    @Column("content")
    private String content;
    @Column("catagoryid")
    private Long catagoryId;
    @Column("authorid")
    private Long authorId;
    @Column("createtime")
    private Date createTime;
    //columns END


    public Post() {
    }

    public Post(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public Long getCatagoryId() {
        return this.catagoryId;
    }

    public void setCatagoryId(Long value) {
        this.catagoryId = value;
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(Long value) {
        this.authorId = value;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date value) {
        this.createTime = value;
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
        if (obj instanceof Post == false) return false;
        if (this == obj) return true;
        Post other = (Post) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

