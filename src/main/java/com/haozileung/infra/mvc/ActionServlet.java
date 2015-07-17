package com.haozileung.infra.mvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public final class ActionServlet extends HttpServlet {

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
	private boolean _process(HttpServletRequest req, HttpServletResponse resp, boolean is_post)
			throws InstantiationException, IllegalAccessException, IOException, IllegalArgumentException,
			InvocationTargetException {
		String requestURI = req.getRequestURI();
		String[] parts = StringUtils.split(requestURI, '/');
		if (parts.length < 2) {
			resp.setStatus(404);
			return false;
		}
		// 加载Action类
		Object action = this._LoadAction(parts[1]);
		if (action == null) {
			resp.setStatus(404);
			return false;
		}
		String action_method_name = (parts.length > 2) ? parts[2] : "index";
		Method m_action = this._GetActionMethod(action, action_method_name);
		if (m_action == null) {
			resp.setStatus(404);
			return false;
		}

		// 调用Action方法之准备参数
		int arg_c = m_action.getParameterTypes().length;
		switch (arg_c) {
		case 0:
			m_action.invoke(action);
			break;
		case 1:
			m_action.invoke(action, req);
			break;
		case 2:
			m_action.invoke(action, req, resp);
			break;
		case 3:
			StringBuilder args = new StringBuilder();
			for (int i = 3; i < parts.length; i++) {
				if (StringUtils.isBlank(parts[i]))
					continue;
				if (args.length() > 0)
					args.append('/');
				args.append(parts[i]);
			}
			boolean isLong = m_action.getParameterTypes()[2].equals(long.class);
			m_action.invoke(action, req, resp, isLong ? NumberUtils.toLong(args.toString(), -1L) : args.toString());
			break;
		default:
			resp.setStatus(404);
			return false;
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
					log("!!!!!!!!! " + action.getClass().getSimpleName() + " destroy !!!!!!!!!");
				}
			} catch (NoSuchMethodException e) {
			} catch (Exception e) {
				log("Unabled to destroy action: " + action.getClass().getSimpleName(), e);
			}
		}
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, false);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp, true);
	}

	@Override
	public void init() throws ServletException {
		String tmp = getInitParameter("packages");
		action_packages = Arrays.asList(StringUtils.split(tmp, ','));
		String initial_actions = getInitParameter("initial_actions");
		for (String action : StringUtils.split(initial_actions, ','))
			try {
				_LoadAction(action);
			} catch (Exception e) {
				log("Failed to initial action : " + action, e);
			}
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
	protected void process(HttpServletRequest req, HttpServletResponse resp, boolean is_post)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		try {
			_process(req, resp, is_post);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}