/**
 *
 */
package com.haozileung.web.controller;

import com.alibaba.fastjson.JSONObject
import com.google.inject.Inject
import com.haozileung.infra.page.PageResult
import com.haozileung.infra.web.BaseServlet
import com.haozileung.infra.web.ReturnStatus
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
class DictionaryController : BaseServlet() {
    private final val logger: Logger = LoggerFactory.getLogger(DictionaryController::class.java);
    @Inject
    lateinit var dictionaryService: IDictionaryService;

    override fun get(req: HttpServletRequest, resp: HttpServletResponse): Any {
        if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
            val dictionary: Dictionary = Dictionary();
            try {
                BeanUtilsBean.getInstance().populate(dictionary, req.parameterMap);
                val offset = Integer.parseInt(req.getParameter("offset"));
                val limit = Integer.parseInt(req.getParameter("limit"));
                return dictionaryService.query(dictionary, offset, limit);
            } catch (e: IllegalAccessException) {
                logger.error(e.message);
            } catch (e: InvocationTargetException) {
                logger.error(e.message);
            }
            return PageResult<Dictionary>();
        } else {
            return "dictionary.html";
        }
    }

    override fun put(req: HttpServletRequest, resp: HttpServletResponse): JSONObject {
        val dictionary: Dictionary = Dictionary();
        val result = JSONObject();
        try {
            BeanUtilsBean.getInstance().populate(dictionary, req.parameterMap);
            dictionary.status = 1 ;
            val id = dictionaryService.save(dictionary);
            if (id > 0) {
                result.put("code", ReturnStatus.SUCCESS);
                result.put("message", "保存成功！");
                return result;
            }
        } catch (e: IllegalAccessException) {
            logger.error(e.message);
        } catch (e: InvocationTargetException) {
            logger.error(e.message);
        }
        result.put("code", ReturnStatus.FAIL);
        result.put("message", "保存失败！");
        return result;
    }

    override fun delete(req: HttpServletRequest, resp: HttpServletResponse): JSONObject {
        val dictionary: Dictionary = Dictionary();
        val result = JSONObject();
        try {
            BeanUtilsBean.getInstance().populate(dictionary, req.parameterMap);
            if (dictionary.dictionaryId != null) {
                val id = dictionaryService.delete(dictionary);
                if (id > 0) {
                    result.put("code", ReturnStatus.SUCCESS);
                    result.put("message", "删除成功！");
                    return result;
                }
            }
        } catch (e: IllegalAccessException) {
            logger.error(e.message);
        } catch (e: InvocationTargetException) {
            logger.error(e.message);
        }
        result.put("code", ReturnStatus.FAIL);
        result.put("message", "删除失败！");
        return result;
    }

    override fun post(req: HttpServletRequest, resp: HttpServletResponse): JSONObject {
        val dictionary: Dictionary = Dictionary();
        val result = JSONObject();
        try {
            BeanUtilsBean.getInstance().populate(dictionary, req.parameterMap);
            if (dictionary.dictionaryId != null) {
                val id = dictionaryService.update(dictionary);
                if (id > 0) {
                    result.put("code", ReturnStatus.SUCCESS);
                    result.put("message", "修改成功！");
                    return result;
                }
            }
        } catch (e: IllegalAccessException) {
            logger.error(e.message);
        } catch (e: InvocationTargetException) {
            logger.error(e.message);
        }
        result.put("code", ReturnStatus.FAIL);
        result.put("message", "修改失败！");
        return result;
    }
}