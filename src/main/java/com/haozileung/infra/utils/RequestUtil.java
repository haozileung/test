package com.haozileung.infra.utils;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class RequestUtil {
	private final static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	public static <T> T getBean(HttpServletRequest request, Class<T> clazz, String dateFormat) {
		HashMap<String, Object> map = Maps.newHashMap();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			map.put(name, request.getParameterValues(name));
		}
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPattern(dateFormat);
		ConvertUtils.register(dateConverter, Date.class);
		T bean = null;
		try {
			bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			bean = null;
		}
		return bean;
	}

	public static <T> T getBean(HttpServletRequest request, Class<T> clazz) {
		return RequestUtil.getBean(request, clazz, "yyyy-MM-dd");
	}

}
