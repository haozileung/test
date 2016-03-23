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
@WebServlet(name = "login", value = "/login", loadOnStartup = 1)
class LoginController : BaseServlet() {
    val logger: Logger = LoggerFactory.getLogger(IndexController::class.java);
    override fun get(req: HttpServletRequest, resp: HttpServletResponse): Any {
        return "login.html";
    }
}