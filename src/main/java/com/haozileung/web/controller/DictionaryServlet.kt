/**
 *
 */
package com.haozileung.web.controller;

import com.google.inject.Inject
import com.haozileung.infra.pager.PageResult
import com.haozileung.infra.web.BaseServlet
import com.haozileung.web.domain.dictionary.Dictionary
import com.haozileung.web.service.dictionary.IDictionaryService
import org.apache.commons.beanutils.BeanUtilsBean
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationTargetException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Haozi
 */
@WebServlet(name = "dictionary", value = "/dictionary", loadOnStartup = 1)
class DictionaryServlet() : BaseServlet() {

    private final val logger: Logger = LoggerFactory.getLogger(DictionaryServlet::class.java);

    @Inject
    lateinit var dictionaryService: IDictionaryService;

    override fun get(req: HttpServletRequest, resp: HttpServletResponse): PageResult<Dictionary> {
        val dictionary: Dictionary = Dictionary();
        try {
            BeanUtilsBean.getInstance().populate(dictionary, req.parameterMap);
            val offset = Integer.parseInt(req.getParameter("offset"));
            val limit = Integer.parseInt(req.getParameter("limit"));
            println(dictionary)
            return dictionaryService.query(dictionary, offset, limit);
        } catch (e: IllegalAccessException) {
            logger.error(e.message, e);
        } catch (e: InvocationTargetException) {
            logger.error(e.message, e);
        }
        return PageResult();
    }
}
