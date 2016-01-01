package com.haozileung.infra.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haozileung.infra.enums.IEnum;
import com.haozileung.infra.utils.ClassUtil;

/**
 * 枚举辅助类
 */
public final class EnumUtils {

	private static final Logger logger = LoggerFactory.getLogger(EnumUtils.class);

	/**
	 * 获取枚举的所有属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static IEnum[] getEnums(Class<?> clazz) {
		if (IEnum.class.isAssignableFrom(clazz)) {
			Object[] enumConstants = clazz.getEnumConstants();
			return (IEnum[]) enumConstants;
		}
		return null;
	}

	/**
	 * 获取枚举的所有属性
	 * 
	 * @param enumClass
	 * @return
	 */
	public static IEnum[] getEnums(String enumClass) {
		try {
			Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(enumClass);
			return getEnums(clazz);
		} catch (ClassNotFoundException e) {
			// ignore
		}
		return null;
	}

	/**
	 * 获取枚举的所有属性
	 *
	 * @param clazz
	 *            the clazz
	 * @param code
	 *            the code
	 * @return enum
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEnum(Class<T> clazz, String code) {
		if (!IEnum.class.isAssignableFrom(clazz)) {
			return null;
		}
		IEnum[] enumConstants = (IEnum[]) clazz.getEnumConstants();
		for (IEnum enumConstant : enumConstants) {
			if (enumConstant.getCode().equalsIgnoreCase(code)) {
				return (T) enumConstant;
			}
		}
		return null;
	}

	/**
	 * 获取枚举的所有属性
	 *
	 * @param clazzName
	 *            the clazzName
	 * @param code
	 *            the code
	 * @return enum
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEnum(String clazzName, String code) {
		Class<?> clazz = null;
		try {
			clazz = ClassUtil.loadClass(clazzName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		if (!IEnum.class.isAssignableFrom(clazz)) {
			return null;
		}
		IEnum[] enumConstants = (IEnum[]) clazz.getEnumConstants();
		for (IEnum enumConstant : enumConstants) {
			if (enumConstant.getCode().equalsIgnoreCase(code)) {
				return (T) enumConstant;
			}
		}
		return null;
	}
}
