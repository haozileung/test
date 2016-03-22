/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.haozileung.web.domain.logging

import java.io.Serializable

/**
 * @author Haozi
 * *
 * @version 1.0
 * *
 * @since 1.0
 */
data class OperateLog(var operateLogId: Long? = null,
                      var operationId: Long? = null,
                      var params: String? = null,
                      var userId: Long? = null,
                      var ipAddress: String? = null,
                      var remarks: String? = null) : Serializable