package com.haozileung.web.domain;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private Long id;

	private String name;

	private String email;

	private Date createTime;

	private String message;

}
