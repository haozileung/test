package com.haozileung.infra.mvc;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.haozileung.infra.dao.annotation.Tx;
import com.haozileung.infra.dao.exceptions.DaoException;
import com.haozileung.infra.dao.transaction.TransactionManager;
import com.haozileung.infra.utils.DataSourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.beetl.ext.servlet.ServletGroupTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private boolean _process(HttpServletRequest req, HttpServletResponse resp)
            throws InstantiationException, IllegalAccessException, IOException, IllegalArgumentException,
            InvocationTargetException {
        String requestURI = req.getRequestURI();
        List<String> parts = Lists.newArrayList(StringUtils.split(requestURI, "/"));
        // 加载Action类
        String action_name = "";
        String method = req.getMethod().toLowerCase();
        if (parts.size() > 0) {
            String cls_name = parts.get(parts.size() - 1);
            parts.remove(parts.size() - 1);
            if (parts.size() > 0) {
                action_name += ".";
                action_name += Joiner.on(".").join(parts);
            }
            action_name += "." + StringUtils.capitalize(cls_name);
        } else {
            action_name = ".Index";
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
                    req.setAttribute("view", "/errors/404.html");
                    render(HttpServletResponse.SC_NOT_FOUND, req, resp);
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
            req.setAttribute("view", "/errors/500.html");
            render(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, req, resp);
        }

        if (result != null) {
            if (result instanceof String) {
                String s = (String) result;
                if (s.startsWith("redirect:")) {
                    s = s.replace("redirect:", "");
                    resp.sendRedirect(s);
                    return true;
                } else {
                    req.setAttribute("view", result);
                }
            } else {
                resp.setContentType("application/json;charset=utf-8");
                req.setAttribute("data", result);
            }
            render(HttpServletResponse.SC_OK, req, resp);
        }
        return true;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String tmp = filterConfig.getInitParameter("packages");
        action_packages = Arrays.asList(StringUtils.split(tmp, ','));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
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
    protected void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        try {
            _process(req, resp);
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

    private void render(int code, HttpServletRequest req, HttpServletResponse resp) {
        String contentType = resp.getContentType();
        resp.setStatus(code);
        if (contentType.indexOf("html") > -1) {
            String view = (String) req.getAttribute("view");
            if (!Strings.isNullOrEmpty(view)) {
                ServletGroupTemplate.instance().render(view, req, resp);
            }
        }
        if (contentType.indexOf("json") > -1) {
            Object data = req.getAttribute("data");
            try {
                PrintWriter pr = resp.getWriter();
                pr.print(JSON.toJSONString(data));
                pr.flush();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}