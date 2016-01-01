/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2012 All Rights Reserved.
 */
package com.haozileung.infra.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 序列化辅助类
 *
 */
public final class SerializeUtils {

    private SerializeUtils() {
    }

    /**
     * 将对象序列化成字符串
     *
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {

        if (obj == null) {
            return null;
        }

        ByteArrayOutputStream baops = null;
        ObjectOutputStream oos = null;
        try {
            baops = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baops);
            oos.writeObject(obj);

            //产生编码问题，用base64保证完整性
            return Base64.encodeBase64String(baops.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("将对象序列化成字符串失败", e);
        } finally {
            IOUtils.closeQuietly(baops);
            IOUtils.closeQuietly(oos);
        }
    }

    /**
     * 将字符串反序列化成对象
     *
     * @param strObj
     * @return
     */
    public static Object stringToObject(String strObj) {

        if (StringUtils.isBlank(strObj)) {
            return null;
        }

        ObjectInputStream ois = null;

        try {
            byte[] bytes = strObj.getBytes();
            ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(Base64.decodeBase64(bytes))));

            Object obj = ois.readObject();
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("将字符串反序列化成对象失败", e);
        } finally {
            IOUtils.closeQuietly(ois);
        }

    }
}
