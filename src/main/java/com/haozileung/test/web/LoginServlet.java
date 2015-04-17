package com.haozileung.test.web;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", urlPatterns = {"/login"}, loadOnStartup = 1)
public class LoginServlet extends VelocityViewServlet {
    /**
     *
     */
    private static final long serialVersionUID = -3696012218714333193L;
    private static final Logger logger = LoggerFactory
            .getLogger(LoginServlet.class);

    // 处理请求
    @Override
    protected Template handleRequest(HttpServletRequest request,
                                     HttpServletResponse response, Context ctx) {
        return getTemplate("/login.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            response.sendRedirect("/index");
        }
        doRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(email)
                    && !Strings.isNullOrEmpty(password));
        } catch (IllegalArgumentException e) {
            logger.error("参数异常", e);
            return;
        }
        logger.debug("用户认证开始:" + email + " , " + password);
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        Subject currentUser = SecurityUtils.getSubject();
        if (!Strings.isNullOrEmpty(rememberMe) && rememberMe.equals("yes")) {
            token.setRememberMe(true);
        }
        try {
            currentUser.login(token);
            logger.debug("用户认证完毕:{}", email);
            response.sendRedirect("/index");
            return;
        } catch (UnknownAccountException uae) {
            logger.debug("用户认证失败:username wasn't in the system.");
        } catch (IncorrectCredentialsException ice) {
            logger.debug("用户认证失败:password didn't match.");
        } catch (LockedAccountException lae) {
            logger.debug("用户认证失败:account for that username is locked - can't login.");
        } catch (AuthenticationException ae) {
            logger.debug("用户认证失败:unexpected condition.");
        }
        request.setAttribute("errorMsg", "登陆失败！");
        this.doRequest(request, response);
    }
}
