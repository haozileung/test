package com.haozileung.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.servlet.ServletGroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexAction {

	private static final Logger logger = LoggerFactory.getLogger(IndexAction.class);

	public void index(HttpServletRequest request, HttpServletResponse response) {
		ServletGroupTemplate.instance().render("/index.html", request, response);
	}

	public void destroy() {
		logger.info("IndexAction destroyed...");
	}

}
