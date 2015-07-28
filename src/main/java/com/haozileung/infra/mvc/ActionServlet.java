package com.haozileung.infra.mvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.beetl.ext.servlet.ServletGroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.haozileung.infra.dao.exceptions.DaoException;

public final class ActionServlet extends HttpServlet {

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, "put");
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, "delete");
	}

	private final static Logger logger = LoggerFactory.getLogger(ActionServlet.class);

	private final static HashMap<String, Object> actions = new HashMap<String, Object>();
	private final static HashMap<String, Method> methods = new HashMap<String, Method>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> action_packages = null;

	/**
	 * 获取名为{method}的方法
	 * 
	 * @param action
	 * @param method
	 * @return
	 */
	private Method _GetActionMethod(Object action, String method) {
		String key = action.getClass().getSimpleName() + '.' + method;
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
				String cls = pkg + '.' + StringUtils.capitalize(act_name) + "Action";
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
			try {
				Method action_init_method = action.getClass().getMethod("init", ServletContext.class);
				action_init_method.invoke(action, getServletContext());
			} catch (NoSuchMethodException e) {
			} catch (InvocationTargetException excp) {
				excp.printStackTrace();
			}
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
	 * 
	 * @param req
	 * @param resp
	 * @param is_post
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private boolean _process(HttpServletRequest req, HttpServletResponse resp, String method)
			throws InstantiationException, IllegalAccessException, IOException, IllegalArgumentException,
			InvocationTargetException {
		String requestURI = req.getRequestURI();
		String[] parts = StringUtils.split(requestURI, '/');
		// 加载Action类
		String action_name = (parts.length > 0) ? parts[0] : "index";
		if (Strings.isNullOrEmpty(action_name)) {
			action_name = "index";
		}
		Object action = this._LoadAction(action_name);
		if (action == null) {
			req.setAttribute("view", "/errors/404.html");
			render(HttpServletResponse.SC_NOT_FOUND, req, resp);
			return false;
		}
		Method m_action = this._GetActionMethod(action, method);
		if (m_action == null) {
			req.setAttribute("view", "/errors/404.html");
			render(HttpServletResponse.SC_NOT_FOUND, req, resp);
			return false;
		}
		// 调用Action方法之准备参数
		int arg_c = m_action.getParameterTypes().length;
		Object result = null;
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
			req.setAttribute("view", "/errors/404.html");
			render(HttpServletResponse.SC_NOT_FOUND, req, resp);
			return false;
		}
		if (result != null) {
			if (result instanceof String) {
				if (((String) result).startsWith("redirect:")) {
					result = ((String) result).replace("redirect:", "");
					resp.sendRedirect((String) result);
				} else {
					req.setAttribute("view", result);
					render(HttpServletResponse.SC_OK, req, resp);
				}
			} else {
				resp.setContentType("application/json;charset=UTF-8");
				req.setAttribute("data", result);
				render(HttpServletResponse.SC_OK, req, resp);
			}
		}
		return true;
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
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, "get");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, "post");
	}

	@Override
	public void init() throws ServletException {
		String tmp = getInitParameter("packages");
		action_packages = Arrays.asList(StringUtils.split(tmp, ','));
	}

	/**
	 * 执行Action方法并进行返回处理、异常处理
	 * 
	 * @param req
	 * @param resp
	 * @param is_post
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp, String method)
			throws ServletException, IOException {
		printParams(req);
		resp.setContentType("text/html;charset=utf-8");
		try {
			_process(req, resp, method);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			logger.info(e.getMessage(), e);
			req.setAttribute("view", "/errors/500.html");
			render(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, req, resp);
		} catch (DaoException e) {
			logger.info(e.getMessage(), e);
			req.setAttribute("view", "/errors/500.html");
			render(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, req, resp);
		}
	}

	private void printParams(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (uri != null && uri.contains(".")) {
			return;
		}
		logger.debug("URL : {}", uri);
		Enumeration<String> paramNames = request.getParameterNames();
		String[] emptyValues = new String[0];
		while (paramNames.hasMoreElements()) {// 遍历Enumeration
			String name = (String) paramNames.nextElement();// 取出下一个元素
			String[] value = MoreObjects.firstNonNull(request.getParameterValues(name), emptyValues);// 获取元素的值
			logger.debug("{} = {}", name, Joiner.on(",").join(value));

		}
	}

	private void render(int code, HttpServletRequest req, HttpServletResponse resp) {
		String contentType = resp.getContentType();
		if (contentType.indexOf("html") > -1) {
			resp.setStatus(code);
			String view = (String) req.getAttribute("view");
			ServletGroupTemplate.instance().render(view, req, resp);
		}
		if (contentType.indexOf("json") > -1) {
			resp.setStatus(code);
			try {
				resp.getWriter().write(JSON.toJSONString(req.getAttribute("data")));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}