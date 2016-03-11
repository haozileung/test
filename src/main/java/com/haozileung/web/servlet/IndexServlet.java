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
@WebServlet(urlPatterns = "/test", loadOnStartup = 1)
public class IndexServlet extends BaseServlet {
    /**
     *
     */
    private static final long serialVersionUID = 4737169523928049554L;

    @Override
    public String get(HttpServletRequest req, HttpServletResponse resp) {

        return null;
    }

}
