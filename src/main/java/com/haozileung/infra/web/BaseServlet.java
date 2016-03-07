/**
 * 
 */
package com.haozileung.infra.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.servlet.ServletGroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

/**
 * @author Haozi
 *
 */
public abstract class BaseServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		if (Initializer.getInjector() != null) {
			Initializer.getInjector().injectMembers(this);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7514613990641762954L;
	private final static Logger logger = LoggerFactory.getLogger(BaseServlet.class);

	protected void renderView(int code, String view, HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("text/html;charset=utf-8");
		resp.setStatus(code);
		if (!Strings.isNullOrEmpty(view)) {
			try {
				ServletGroupTemplate.instance().render(view, req, resp);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				try {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				} catch (IOException e1) {
					logger.error(e1.getMessage(), e1);
				}
			}
		}
	}

	protected void renderJSON(int code, Object data, HttpServletResponse resp) {
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

	protected void render(Object result, HttpServletRequest req, HttpServletResponse resp) {
		if (result != null) {
			if (result instanceof String) {
				String s = (String) result;
				if (s.startsWith("redirect:")) {
					s = s.replace("redirect:", "");
					try {
						resp.sendRedirect(s);
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				renderView(resp.getStatus(), s, req, resp);
			} else {
				renderJSON(resp.getStatus(), result, resp);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		Object obj = get(req, resp);
		render(obj, req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		Object obj = post(req, resp);
		render(obj, req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
		Object obj = put(req, resp);
		render(obj, req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		Object obj = delete(req, resp);
		render(obj, req, resp);
	}

	public Object get(HttpServletRequest req, HttpServletResponse resp) {
		return null;
	};

	public Object post(HttpServletRequest req, HttpServletResponse resp) {
		return null;
	};

	public Object put(HttpServletRequest req, HttpServletResponse resp) {
		return null;
	};

	public Object delete(HttpServletRequest req, HttpServletResponse resp) {
		return null;
	};
}
