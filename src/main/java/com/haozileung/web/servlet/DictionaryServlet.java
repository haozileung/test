/**
 *
 */
package com.haozileung.web.servlet;

import com.google.inject.Inject;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.infra.web.BaseServlet;
import com.haozileung.web.domain.dictionary.Dictionary;
import com.haozileung.web.service.dictionary.IDictionaryService;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Haozi
 */
@WebServlet(urlPatterns = "/dictionary", loadOnStartup = 1)
public class DictionaryServlet extends BaseServlet {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryServlet.class);
    /**
     *
     */
    private static final long serialVersionUID = 4737169523928049554L;

    @Inject
    private IDictionaryService dictionaryService;

    @Override
    public PageResult<Dictionary> get(HttpServletRequest req, HttpServletResponse resp) {
        Dictionary dictionary = new Dictionary();
        PageResult<Dictionary> result = new PageResult<>();
        try {
            BeanUtilsBean.getInstance().populate(dictionary, req.getParameterMap());
            int pageNo = Integer.parseInt(req.getParameter("pageNo"));
            result = dictionaryService.query(dictionary,pageNo,20);
            return result;
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }finally {
            return result;
        }
    }
}
