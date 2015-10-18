package com.haozileung.infra.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.beetl.ext.servlet.ServletGroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.haozileung.infra.dao.annotation.Tx;
import com.haozileung.infra.dao.exceptions.DaoException;
import com.haozileung.infra.dao.transaction.TransactionManager;
import com.haozileung.infra.utils.DataSourceUtil;

public final class ActionDispatcher implements Filter {

	private final static Logger logger = LoggerFactory.getLogger(ActionDispatcher.class);
	private final static HashMap<String, Object> actions = new HashMap<String, Object>();
	private final static HashMap<String, Method> methods = new HashMap<String, Method>();
	private List<String> action_packages = null;

	/**
	 * 获取名为{method}的方法
	 *
	 * @param action
	 * @param method
	 * @return
	 */
	private Method _GetActionMethod(Object action, String method) {
		String key = action.getClass().getName() + '.' + method;
		Method m = methods.get(key);
		if (m != null)
			return m;
		for (Method m1 : action.getClass().getMethods()) {
			if (m1.getModifiers() == Modifier.PUBLIC && m1.getName().equals(method)) {
				synchronized (methods) {
					methods.put(key, m1);
				}
				return m1;
			}
		}
		return null;
	}

	/**
	 * 加载Action类
	 *
	 * @param act_name
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	protected Object _LoadAction(String act_name) throws InstantiationException, IllegalAccessException {
		Object action = actions.get(act_name);
		if (action == null) {
			for (String pkg : action_packages) {
				String cls = pkg + act_name + "Action";
				action = _LoadActionOfFullname(act_name, cls);
				if (action != null)
					break;
			}
		}
		return action;
	}

	private Object _LoadActionOfFullname(String act_name, String cls)
			throws IllegalAccessException, InstantiationException {
		Object action = null;
		try {
			action = Class.forName(cls).newInstance();
			if (!actions.containsKey(act_name)) {
				synchronized (actions) {
					actions.put(act_name, action);
				}
			}
		} catch (ClassNotFoundException excp) {
		}
		return action;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private boolean _process(HttpServletRequest req, HttpServletResponse resp) throws InstantiationException,
			IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
		String requestURI = req.getRequestURI();
		List<String> parts = Lists.newArrayList(StringUtils.split(requestURI, "/"));
		// 加载Action类
		String action_name = ".";
		String method = req.getMethod().toLowerCase();
		if (parts.size() > 0) {
			int i = 0;
			for (; i < parts.size() - 1; i++) {
				action_name += parts.get(i);
				action_name += ".";
			}
			action_name += StringUtils.capitalize(parts.get(i));
		} else {
			action_name += "Index";
		}
		Object action = this._LoadAction(action_name);
		if (action == null) {
			render(HttpServletResponse.SC_NOT_FOUND, "/errors/404.html", req, resp);
			return false;
		}
		Method m_action = this._GetActionMethod(action, method);
		if (m_action == null) {
			render(HttpServletResponse.SC_NOT_FOUND, "/errors/404.html", req, resp);
			return false;
		}
		// 调用Action方法之准备参数
		int arg_c = m_action.getParameterTypes().length;
		Object result = null;
		boolean tx = m_action.isAnnotationPresent(Tx.class);
		TransactionManager tm = null;
		if (tx) {
			tm = DataSourceUtil.getTranManager();
			tm.beginTransaction();
		}
		try {
			switch (arg_c) {
			case 0:
				result = m_action.invoke(action);
				break;
			case 1:
				result = m_action.invoke(action, req);
				break;
			case 2:
				result = m_action.invoke(action, req, resp);
				break;
			default:
				render(HttpServletResponse.SC_NOT_FOUND, "/errors/404.html", req, resp);
				if (tx && tm != null) {
					tm.commitAndClose();
				}
				return false;
			}
			if (tx && tm != null) {
				tm.commitAndClose();
			}
		} catch (Exception e) {
			if (tx && tm != null) {
				tm.rollbackAndClose();
			}
			logger.info(e.getMessage(), e);
			render(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "/errors/500.html", req, resp);
		}

		if (result != null) {
			if (result instanceof String) {
				String s = (String) result;
				if (s.startsWith("redirect:")) {
					s = s.replace("redirect:", "");
					resp.sendRedirect(s);
					return true;
				}
				render(HttpServletResponse.SC_OK, s, req, resp);
			} else {
				renderJSON(HttpServletResponse.SC_OK, result, resp);
			}

		}
		return true;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String tmp = filterConfig.getInitParameter("packages");
		action_packages = Arrays.asList(StringUtils.split(tmp, ','));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String tmp = req.getRequestURI();
		if (tmp.contains(".")) {
			chain.doFilter(request, response);
		} else {
			process(req, res);
		}
	}

	@Override
	public void destroy() {
		for (Object action : actions.values()) {
			try {
				Method dm = action.getClass().getMethod("destroy");
				if (dm != null) {
					dm.invoke(action);
					logger.info("!!!!!!!!! " + action.getClass().getSimpleName() + " destroy !!!!!!!!!");
				}
			} catch (NoSuchMethodException e) {
			} catch (Exception e) {
				logger.info("Unabled to destroy action: " + action.getClass().getSimpleName(), e);
			}
		}
	}

	/**
	 * 执行Action方法并进行返回处理、异常处理
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			_process(req, resp);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.info(e.getMessage(), e);
			render(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "/errors/500.html", req, resp);
		} catch (DaoException e) {
			logger.info(e.getMessage(), e);
			render(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "/errors/500.html", req, resp);
		}
	}

	private void render(int code, String view, HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("text/html;charset=utf-8");
		resp.setStatus(code);
		if (!Strings.isNullOrEmpty(view)) {
			ServletGroupTemplate.instance().render(view, req, resp);
		}
	}

	private void renderJSON(int code, Object data, HttpServletResponse resp) {
		resp.setContentType("application/json;charset=utf-8");
		resp.setStatus(code);
		try {
			PrintWriter pr = resp.getWriter();
			pr.print(JSON.toJSONString(data));
			pr.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}