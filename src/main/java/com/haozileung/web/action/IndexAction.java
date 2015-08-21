package com.haozileung.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.haozileung.infra.dao.exceptions.DaoException;
import com.haozileung.infra.dao.persistence.TransactionManager;
import com.haozileung.infra.utils.DataSourceUtil;

public class IndexAction {

	public String get(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		request.setAttribute("code", 0);
		request.setAttribute("message", "測試test");
		return "/index.html";
	}

	public Map<String, Object> post(HttpServletRequest request, HttpServletResponse response) {
		TransactionManager tx = DataSourceUtil.getTranManager();
		try {
			tx.beginTransaction();
			// service code in here
			tx.commitAndClose();
		} catch (DaoException e) {
			tx.rollbackAndClose();
		}
		Map<String, Object> result = Maps.newHashMap();
		result.put("code", 0);
		result.put("message", "測試test");
		return result;
	}

	public Map<String, Object> put(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("code", 0);
		result.put("message", "測試test");
		return result;
	}

	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("code", 0);
		result.put("message", "測試test");
		return result;
	}
}
