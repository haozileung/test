/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package com.haozileung.web.domain.dictionary;

import java.io.Serializable;

/**
 * @author Haozi
 * @version 1.0
 * @since 1.0
 */
data class Dictionary(var dictionaryId: Long? = null,
                      var dicTypeId: Long? = null,
                      var code: String? = null,
                      var name: String? = null,
                      var remarks: String? = null,
                      var status: Int? = null) : Serializable
