package com.haozileung.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexAction {

	private static final Logger logger = LoggerFactory.getLogger(IndexAction.class);

	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "/index.html";
	}
}
