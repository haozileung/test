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
import java.util.Map;

/**
 * Created by Haozi on 2015/9/28.
 */
public class UserAction {
    public String get(HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getHeader("Accept");
        if (!Strings.isNullOrEmpty(contentType) && contentType.contains("json")) {
            response.setContentType("application/json;charset=utf-8");
            String name = request.getParameter("name");
            String status = request.getParameter("status");
            Criteria c = Criteria.create(User.class).exclude("password").limit(0, 10);
            if (!Strings.isNullOrEmpty(name)) {
                c = c.and("name", new Object[]{name});
            }
            if (!Strings.isNullOrEmpty(status)) {
                c = c.and("status", new Object[]{status});
            }
            Pager page = new Pager();
            page.setItemsPerPage(10);
            page.setCurPage(1);
            page.setList(JdbcDaoUtil.getInstance().queryList(c));
            page.setItemsTotal(JdbcDaoUtil.getInstance().queryCount(c));
            request.setAttribute("data", page);
        }
        return "admin/user.html";
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
