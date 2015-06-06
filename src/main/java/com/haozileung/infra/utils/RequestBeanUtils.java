package com.haozileung.infra.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.google.common.collect.Maps;

public class RequestBeanUtils {

	public <T> T getBean(HttpServletRequest request, Class<T> clazz,
			String dateFormat) {
		HashMap<String, Object> map = Maps.newHashMap();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			map.put(name, request.getParameterValues(name));
		}
		DateConverter dateConverter = new DateConverter();
		if (dateFormat == null) {
			dateFormat = "yyyy-MM-dd";
		}
		dateConverter.setPattern(dateFormat);
		ConvertUtils.register(dateConverter, Date.class);
		T bean = null;
		try {
			bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
		} catch (InstantiationException | IllegalAccessException e1) {
			bean = null;
		} catch (InvocationTargetException e) {
			bean = null;
		}
		return bean;
	}
}
