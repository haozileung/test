/**
 * 
 */
package com.haozileung.infra.servlet;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 7514613990641762954L;
	private final static Logger logger = LoggerFactory.getLogger(BaseServlet.class);

	private void renderView(int code, String view, HttpServletRequest req, HttpServletResponse resp) {
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

	private void render(Object result, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (result != null) {
			if (result instanceof String) {
				String s = (String) result;
				if (s.startsWith("redirect:")) {
					s = s.replace("redirect:", "");
					resp.sendRedirect(s);
				}
				renderView(HttpServletResponse.SC_OK, s, req, resp);
			} else {
				renderJSON(HttpServletResponse.SC_OK, result, resp);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object obj = get(req, resp);
		render(obj, req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object obj = null;
		render(obj, req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object obj = null;
		render(obj, req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object obj = null;
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