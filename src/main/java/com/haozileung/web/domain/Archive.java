package com.haozileung.web.domain;

import java.io.Serializable;
import java.util.List;

public class Archive implements Serializable {

	private Long id;

	private Integer year;

	private Integer month;

	private List<Post> posts;

}
