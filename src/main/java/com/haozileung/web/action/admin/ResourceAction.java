package com.haozileung.web.action.admin;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.haozileung.infra.dao.pager.Pager;
import com.haozileung.infra.dao.persistence.Criteria;
import com.haozileung.infra.dao.persistence.JdbcDaoUtil;
import com.haozileung.infra.mvc.JsonServlet;
import com.haozileung.infra.utils.RegexUtil;
import com.haozileung.infra.utils.RequestUtil;
import com.haozileung.web.domain.system.Resource;

/**
 * Created by Haozi on 2015/9/28.
 */
@WebServlet(urlPatterns = "/admin/resource")
public class ResourceAction extends JsonServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9099978604252799819L;

	public Object get(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String groupId = request.getParameter("groupId");
		String type = request.getParameter("type");
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String status = request.getParameter("status");
		String pageNo = request.getParameter("pageNo");
		String isAll = request.getParameter("isAll");
		Criteria c = Criteria.create(Resource.class).asc("orderNo");
		if (!Strings.isNullOrEmpty(id)) {
			c = c.and("id", new Object[] { id });
			return JdbcDaoUtil.getInstance().querySingleResult(c);
		}
		if (!Strings.isNullOrEmpty(groupId)) {
			c = c.and("groupId", new Object[] { groupId });
		}
		if (!Strings.isNullOrEmpty(type)) {
			c = c.and("type", new Object[] { type });
		}
		if (!Strings.isNullOrEmpty(code)) {
			c = c.and("code", new Object[] { code });
		}
		if (!Strings.isNullOrEmpty(name)) {
			c = c.and("name", new Object[] { name });
		}
		if (!Strings.isNullOrEmpty(status)) {
			c = c.and("status", new Object[] { status });
		}
		if ("true".equalsIgnoreCase(isAll)) {
			return JdbcDaoUtil.getInstance().queryList(c);
		} else {
			int page = Strings.isNullOrEmpty(pageNo)
					|| !StringUtils.isNumeric(pageNo) ? 0 : Integer
					.valueOf(pageNo);
			Pager p = new Pager();
			p.setCurPage(page);
			c.limit(p.getOffset(), p.getItemsPerPage());
			p.setItemsTotal(JdbcDaoUtil.getInstance().queryCount(c));
			p.setList(JdbcDaoUtil.getInstance().queryList(c));
			return p;
		}
	}

	public Map<String, Object> post(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		Resource obj = RequestUtil.getBean(request, Resource.class);
		if (RegexUtil.isNull(obj.getId())) {
			data.put("error", "参数错误！");
			return data;
		}
		Long count = JdbcDaoUtil.getInstance().queryCount(
				Criteria.create(Resource.class)
						.where("code", new Object[] { obj.getCode() })
						.and("groupId", new Object[] { obj.getGroupId() })
						.and("id", "<>", new Object[] { obj.getId() }));
		if (count != null && count > 0) {
			data.put("code", "0001");
			data.put("error", "资源编码重复！");
			return data;
		}
		JdbcDaoUtil.getInstance().update(obj);
		data.put("code", "0000");
		data.put("msg", "修改成功！");
		return data;
	}

	public Map<String, Object> put(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		Resource obj = RequestUtil.getBean(request, Resource.class);
		Long count = JdbcDaoUtil.getInstance().queryCount(
				Criteria.create(Resource.class)
						.where("code", new Object[] { obj.getCode() })
						.and("groupId", new Object[] { obj.getGroupId() }));
		if (count != null && count > 0) {
			data.put("code", "0001");
			data.put("error", "资源编码重复！");
			return data;
		}
		JdbcDaoUtil.getInstance().save(obj);
		data.put("code", "0000");
		data.put("msg", "添加成功！");
		return data;
	}

	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> data = Maps.newHashMap();
		String ids = request.getParameter("id");
		if (Strings.isNullOrEmpty(ids)) {
			data.put("error", "参数错误！");
			return data;
		}
		Splitter.on(",").split(ids).forEach(id -> {
			Integer i = Integer.valueOf(id);
			Resource obj = new Resource();
			obj.setId(i.longValue());
			JdbcDaoUtil.getInstance().delete(obj);
		});
		data.put("code", "0000");
		data.put("msg", "删除成功！");
		return data;
	}
}
