/**
 *
 */
package com.haozileung.web.controller;

import com.haozileung.infra.pager.PageResult
import com.haozileung.infra.web.BaseServlet
import com.haozileung.infra.web.Initializer
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
@WebServlet(name = "dictionary", value = "/dictionary",loadOnStartup = 1)
class DictionaryServlet() : BaseServlet() {

    private final val logger:Logger = LoggerFactory.getLogger(DictionaryServlet::class.java);

    val dictionaryService: IDictionaryService = Initializer.instance?.getInstance(IDictionaryService::class.java)!!;

    override fun  get(req:HttpServletRequest, resp:HttpServletResponse) :PageResult<Dictionary>{
        val  dictionary :Dictionary =  Dictionary();
        var result:PageResult<Dictionary>  = PageResult();
        try {
            BeanUtilsBean.getInstance().populate(dictionary, req.parameterMap);
            val offset = Integer.parseInt(req.getParameter("offset"));
            val  limit = Integer.parseInt(req.getParameter("limit"));
            result = dictionaryService.query(dictionary,offset,limit);
        } catch ( e:IllegalAccessException) {
            logger.error(e.message, e);
        } catch ( e:InvocationTargetException) {
            logger.error(e.message, e);
        }finally {
            return result;
        }
    }
}
