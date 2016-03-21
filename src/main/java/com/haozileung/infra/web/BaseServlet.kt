package com.haozileung.infra.web;

import com.alibaba.fastjson.JSON
import com.google.common.base.Strings
import com.google.inject.Injector
import org.beetl.ext.servlet.ServletGroupTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.PrintWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class BaseServlet : HttpServlet() {
    private final val logger: Logger = LoggerFactory.getLogger(BaseServlet::class.java);

    override fun init() {
        (Initializer.instance as Injector).injectMembers(this);
    }

    fun renderView(code: Int, view: String, req: HttpServletRequest, resp: HttpServletResponse) {
        resp.contentType = "text/html;charset=utf-8";
        resp.status = code;
        if (!Strings.isNullOrEmpty(view)) {
            try {
                ServletGroupTemplate.instance().render(view, req, resp);
            } catch (e: Exception) {
                logger.error(e.message, e);
                try {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (e1: IOException) {
                    logger.error(e1.message, e1);
                }
            }
        }
    }

    fun renderJSON(code: Int, data: Any?, resp: HttpServletResponse) {
        resp.contentType = "application/json;charset=utf-8";
        resp.status = code;
        try {
            var pr: PrintWriter = resp.writer;
            pr.print(JSON.toJSONString(data));
            pr.flush();
        } catch (e: IOException) {
            logger.error(e.message, e);
        }
    }

    fun render(result: Any?, req: HttpServletRequest, resp: HttpServletResponse) {
        if (result != null) {
            if (result is String) {
                if (result.startsWith("redirect:")) {
                    val v = result.replace("redirect:", "");
                    try {
                        resp.sendRedirect(v);
                    } catch (e: IOException) {
                        logger.error(e.message, e);
                    }
                }
                renderView(resp.status, result, req, resp);
            } else {
                renderJSON(resp.status, result, resp);
            }
        }
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val obj: Any = get(req, resp);
        render(obj, req, resp);
    }


    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val obj: Any = get(req, resp);
        render(obj, req, resp);
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        val obj: Any = get(req, resp);
        render(obj, req, resp);
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        val obj: Any = get(req, resp);
        render(obj, req, resp);
    }

    open fun get(req: HttpServletRequest, resp: HttpServletResponse): Any {
        return Any();
    }

    open fun post(req: HttpServletRequest, resp: HttpServletResponse): Any {
        return Any();
    }

    open fun put(req: HttpServletRequest, resp: HttpServletResponse): Any {
        return Any();
    }

    open fun delete(req: HttpServletRequest, resp: HttpServletResponse): Any {
        return Any();
    }

}