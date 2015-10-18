package com.haozileung.web.action.admin;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.haozileung.infra.dao.pager.Pager;
import com.haozileung.infra.dao.persistence.Criteria;
import com.haozileung.infra.dao.persistence.JdbcDaoUtil;
import com.haozileung.infra.utils.RegexUtil;
import com.haozileung.infra.utils.RequestUtil;
import com.haozileung.web.domain.system.Status;
import com.haozileung.web.domain.system.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Haozi on 2015/9/28.
 */
public class UserAction {
	public Pager get(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		String pageNo = request.getParameter("pageNo");
		int pageSize = 10;
		int page = Strings.isNullOrEmpty(pageNo) || !StringUtils.isNumeric(pageNo) ? 0 : Integer.valueOf(pageNo);
		int startRow = (page - 1) * pageSize;
		if (startRow < 0) {
			startRow = 0;
		}
		Criteria c = Criteria.create(User.class).exclude("password").limit(startRow, pageSize);
		if (!Strings.isNullOrEmpty(name)) {
			c = c.and("name", new Object[] { name });
		}
		if (!Strings.isNullOrEmpty(status)) {
			c = c.and("status", new Object[] { status });
		} else {
			c = c.where("status", "<>", new Object[] { 1 });
		}
		Pager p = new Pager();
		p.setItemsPerPage(pageSize);
		p.setCurPage(page);
		p.setItemsTotal(JdbcDaoUtil.getInstance().queryCount(c));
		p.setList(JdbcDaoUtil.getInstance().queryList(c));
		return p;
	}

	public Map<String, Object> post(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		User user = RequestUtil.getBean(request, User.class, null);
		if (RegexUtil.isNull(user.getId())) {
			data.put("error", "参数错误！");
			return data;
		}
		JdbcDaoUtil.getInstance().update(user);
		return data;
	}

	public Map<String, Object> put(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		User user = RequestUtil.getBean(request, User.class, null);
		user.setStatus(Status.ENABLED.ordinal());
		JdbcDaoUtil.getInstance().save(user);
		return data;
	}

	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		String ids = request.getParameter("id");
		if (Strings.isNullOrEmpty(ids)) {
			data.put("error", "参数错误！");
			return data;
		}
		Splitter.on(",").split(ids).forEach(id -> {
			Integer i = Integer.valueOf(id);
			User user = new User();
			user.setId(i.longValue());
			user.setStatus(Status.DISABLED.ordinal());
			JdbcDaoUtil.getInstance().update(user);
		});
		data.put("success", "删除成功！");
		return data;
	}
}
