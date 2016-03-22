/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.haozileung.web.domain.logging

import java.io.Serializable
import java.util.*

/**
 * @author Haozi
 * *
 * @version 1.0
 * *
 * @since 1.0
 */

data class LoginLog(var loginLogId: Long? = null,
                    var userId: Long? = null,
                    var ipAddress: String? = null,
                    var loginTime: Date? = null,
                    var result: Int? = null,
                    var browser: String? = null,
                    var remarks: String? = null) : Serializable