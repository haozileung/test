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

public class RequestUtil {

	public static <T> T getBean(HttpServletRequest request, Class<T> clazz, String dateFormat) {
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

	public static String getClientContextType(HttpServletRequest request) {
		Enumeration<String> accept = request.getHeaders("Accept");
		Enumeration<String> charset = request.getHeaders("Accept-Charset");
		String contentType = "text/html";
		float currentOrder = 0f;
		while (accept.hasMoreElements()) {
			String s = accept.nextElement();
			String[] a = s.split(",");
			boolean found = false;
			for (int i = 0; i < a.length; i++) {
				String[] b = a[i].split(";");
				if (b.length < 2) {
					contentType = b[0];
					currentOrder = 1;
					found = true;
					break;
				} else {
					String qs = b[1].split("=")[1];
					float q = Float.valueOf(qs);
					if (q > currentOrder) {
						contentType = b[0];
						currentOrder = Float.valueOf(q);
					}
				}
			}
			if (found) {
				break;
			}
		}
		String chartSet = "charset=utf-8";
		currentOrder = 0f;
		while (charset.hasMoreElements()) {
			String s = charset.nextElement();
			String[] a = s.split(",");
			boolean found = false;
			for (int i = 0; i < a.length; i++) {
				String[] b = a[i].split(";");
				if (b.length < 2) {
					chartSet = b[0];
					currentOrder = 1;
					found = true;
					break;
				} else {
					String qs = b[1].split("=")[1];
					float q = Float.valueOf(qs);
					if (q > currentOrder) {
						chartSet = b[0];
						currentOrder = Float.valueOf(q);
					}
					if (q == 1f) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				break;
			}
		}
		if ("*".equals(contentType)) {
			contentType = "text/html";
		}
		if ("*".equals(chartSet)) {
			chartSet = "charset=utf-8";
		}
		return contentType + ";" + chartSet;
	}
}
