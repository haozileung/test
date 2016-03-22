/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.haozileung.web.domain.permission

import java.io.Serializable

/**
 * @author Haozi
 * *
 * @version 1.0
 * *
 * @since 1.0
 */

data class User(var userId: Long? = null,
                var username: String? = null,
                var password: String? = null,
                var email: String? = null,
                var remarks: String? = null,
                var status: Int? = null) : Serializable {
}
