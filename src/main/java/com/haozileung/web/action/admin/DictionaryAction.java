package com.haozileung.web.action.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.haozileung.infra.dao.pager.Pager;
import com.haozileung.infra.dao.persistence.Criteria;
import com.haozileung.infra.dao.persistence.JdbcDaoUtil;
import com.haozileung.infra.utils.RegexUtil;
import com.haozileung.infra.utils.RequestUtil;
import com.haozileung.web.domain.system.Dictionary;
import com.haozileung.web.domain.system.Status;

/**
 * Created by Haozi on 2015/9/28.
 */
public class DictionaryAction {
	public Object get(HttpServletRequest request, HttpServletResponse response) {
		String parentId = request.getParameter("parentId");
		String code = request.getParameter("code");
		String value = request.getParameter("value");
		String status = request.getParameter("status");
		String pageNo = request.getParameter("pageNo");
		String isAll = request.getParameter("isAll");
		Criteria c = Criteria.create(Dictionary.class).asc("orderNo");
		if (!Strings.isNullOrEmpty(parentId)) {
			c = c.and("parentId", new Object[] { parentId });
		}
		if (!Strings.isNullOrEmpty(code)) {
			c = c.and("code", new Object[] { code });
		}
		if (!Strings.isNullOrEmpty(value)) {
			c = c.and("value", new Object[] { value });
		}
		if (!Strings.isNullOrEmpty(status)) {
			c = c.and("status", new Object[] { status });
		} else {
			c = c.where("status", new Object[] { 0 });
		}
		if (!"true".equalsIgnoreCase(isAll)) {
			int page = Strings.isNullOrEmpty(pageNo) || !StringUtils.isNumeric(pageNo) ? 0 : Integer.valueOf(pageNo);
			Pager p = new Pager();
			p.setCurPage(page);
			c.limit(p.getOffset(), p.getItemsPerPage());
			p.setItemsTotal(JdbcDaoUtil.getInstance().queryCount(c));
			p.setList(JdbcDaoUtil.getInstance().queryList(c));
			return p;
		} else {
			return JdbcDaoUtil.getInstance().queryList(c);
		}

	}

	public Map<String, Object> post(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		Dictionary dic = RequestUtil.getBean(request, Dictionary.class);
		if (RegexUtil.isNull(dic.getId())) {
			data.put("error", "参数错误！");
			return data;
		}
		JdbcDaoUtil.getInstance().update(dic);
		return data;
	}

	public Map<String, Object> put(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		Dictionary dic = RequestUtil.getBean(request, Dictionary.class);
		dic.setStatus(Status.ENABLED.ordinal());
		JdbcDaoUtil.getInstance().save(dic);
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
			Dictionary dic = new Dictionary();
			dic.setId(i.longValue());
			dic.setStatus(Status.DISABLED.ordinal());
			JdbcDaoUtil.getInstance().update(dic);
		});
		data.put("success", "删除成功！");
		return data;
	}
}
