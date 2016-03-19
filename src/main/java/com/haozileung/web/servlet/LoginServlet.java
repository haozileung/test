/**
 *
 */
package com.haozileung.web.servlet;

import com.haozileung.infra.web.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Haozi
 */
@WebServlet(name = "login", urlPatterns = "/login", loadOnStartup = 1)
public class LoginServlet extends BaseServlet {
    /**
     *
     */
    private static final long serialVersionUID = 4737169523928049554L;

    @Override
    public String get(HttpServletRequest req, HttpServletResponse resp) {
        return "login.html";
    }

}
