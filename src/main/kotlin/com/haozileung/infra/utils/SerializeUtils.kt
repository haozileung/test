/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.haozileung.infra.utils

import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils

import java.io.*

/**
 * 序列化辅助类
 */
object SerializeUtils {

    /**
     * 将对象序列化成字符串

     * @param obj
     * *
     * @return
     */
    fun objectToString(obj: Any?): String? {

        if (obj == null) {
            return null
        }

        var baops: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baops = ByteArrayOutputStream()
            oos = ObjectOutputStream(baops)
            oos.writeObject(obj)

            // 产生编码问题，用base64保证完整性
            return Base64.encodeBase64String(baops.toByteArray())

        } catch (e: IOException) {
            throw RuntimeException("将对象序列化成字符串失败", e)
        } finally {
            IOUtils.closeQuietly(baops)
            IOUtils.closeQuietly(oos)
        }
    }

    /**
     * 将字符串反序列化成对象

     * @param strObj
     * *
     * @return
     */
    fun stringToObject(strObj: String): Any? {

        if (StringUtils.isBlank(strObj)) {
            return null
        }

        var ois: ObjectInputStream? = null

        try {
            val bytes = strObj.toByteArray()
            ois = ObjectInputStream(BufferedInputStream(ByteArrayInputStream(Base64.decodeBase64(bytes))))

            val obj = ois.readObject()
            return obj
        } catch (e: Exception) {
            throw RuntimeException("将字符串反序列化成对象失败", e)
        } finally {
            IOUtils.closeQuietly(ois)
        }

    }
}
