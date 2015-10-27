package com.haozileung.web.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.haozileung.infra.dao.persistence.JdbcDaoUtil;

public class SelectAction {
	public Object get(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		if (Strings.isNullOrEmpty(type)) {
			type = "-1";
		}
		String sql = "select code id, value text from t_dictionary where parent_code = ? and status = 100 order by order_no";
		return JdbcDaoUtil.getInstance().queryForMapList(sql,
				new Object[] { type });
	}
}
