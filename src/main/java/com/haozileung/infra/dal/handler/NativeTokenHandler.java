package com.haozileung.infra.dal.handler;

import org.apache.commons.lang3.StringUtils;

/**
 * 解析[]符号
 * 
 * Created by yamcha on 2015-12-8.
 */
public class NativeTokenHandler implements TokenHandler {

	private Class<?> clazz;
	private String alias;
	private NameHandler nameHandler;

	public NativeTokenHandler(Class<?> clazz, String alias, NameHandler nameHandler) {
		this.clazz = clazz;
		this.alias = alias;
		this.nameHandler = nameHandler;
	}

	public String handleToken(String content) {
		String columnName = nameHandler.getColumnName(this.clazz, content);
		if (StringUtils.isBlank(alias)) {
			return columnName;
		}
		return new StringBuilder(alias).append(".").append(columnName).toString();
	}
}
