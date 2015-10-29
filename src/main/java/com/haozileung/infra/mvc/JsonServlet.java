/**
 * 
 */
package com.haozileung.infra.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.haozileung.infra.dao.transaction.TransactionManager;
import com.haozileung.infra.utils.DataSourceUtil;

/**
 * @author Haozi
 *
 */

public abstract class JsonServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7514613990641762954L;
	private final static Logger logger = LoggerFactory.getLogger(JsonServlet.class);

	private void renderJSON(Object data, HttpServletResponse resp) {
		resp.setContentType("application/json;charset=utf-8");
		try {
			PrintWriter pr = resp.getWriter();
			pr.print(JSON.toJSONString(data));
			pr.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object obj = get(req, resp);
		DataSourceUtil.closeConnection();
		if (obj != null) {
			renderJSON(obj, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TransactionManager tm = DataSourceUtil.getTranManager();
		tm.beginTransaction();
		Object obj = null;
		try {
			obj = post(req, resp);
			tm.commitAndClose();
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
			tm.rollbackAndClose();
		}
		if (obj != null) {
			renderJSON(obj, resp);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TransactionManager tm = DataSourceUtil.getTranManager();
		tm.beginTransaction();
		Object obj = null;
		try {
			obj = put(req, resp);
			tm.commitAndClose();
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
			tm.rollbackAndClose();
		}
		if (obj != null) {
			renderJSON(obj, resp);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TransactionManager tm = DataSourceUtil.getTranManager();
		tm.beginTransaction();
		Object obj = null;
		try {
			obj = delete(req, resp);
			tm.commitAndClose();
		} catch (RuntimeException re) {
			logger.error(re.getMessage());
			tm.rollbackAndClose();
		}
		if (obj != null) {
			renderJSON(obj, resp);
		}
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
