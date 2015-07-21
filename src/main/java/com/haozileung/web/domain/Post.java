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

}
