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
data class Permission(var permissionId: Long? = null,
                      var name: String? = null,
                      var type: String? = null,
                      var code: String? = null,
                      var remarks: String? = null,
                      var status: Int? = null) : Serializable
