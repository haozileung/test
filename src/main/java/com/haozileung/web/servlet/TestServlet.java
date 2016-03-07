/**
 *
 */
package com.haozileung.web.servlet;

import com.google.inject.Inject;
import com.haozileung.infra.web.BaseServlet;
import com.haozileung.web.service.IMyService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Haozi
 */
@WebServlet(urlPatterns = "/test", loadOnStartup = 1)
public class TestServlet extends BaseServlet {
    /**
     *
     */
    private static final long serialVersionUID = 4737169523928049554L;
    @Inject
    private IMyService service;

    @Override
    public String get(HttpServletRequest req, HttpServletResponse resp) {
        service.test();
        return null;
    }

}
