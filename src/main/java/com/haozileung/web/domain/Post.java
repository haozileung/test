package com.haozileung.web.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post implements Serializable {

    private Long id;

    private String title;

    private String content;

    private Author author;

    private Date createTime;

    private Integer viewCount;

    private List<Comment> comments;

    private List<Catagory> catagories;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Catagory> getCatagories() {
        return catagories;
    }

    public void setCatagories(List<Catagory> catagories) {
        this.catagories = catagories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
