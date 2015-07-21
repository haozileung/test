package com.haozileung.web.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Message implements Serializable {

	private Long id;

	private String name;

	private String email;

	private Date createTime;

	private String message;

	private List<Message> replies;

}
