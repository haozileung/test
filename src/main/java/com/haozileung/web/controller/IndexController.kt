package com.haozileung.web.controller

import com.haozileung.infra.web.BaseServlet
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by haozi on 16-3-20.
 */
@WebServlet(name = "index", value = "/index", loadOnStartup = 1)
class IndexController : BaseServlet() {
    val logger: Logger = LoggerFactory.getLogger(IndexController::class.java);
    override fun get(req: HttpServletRequest, resp: HttpServletResponse): Any {
        return "index.html";
    }
}