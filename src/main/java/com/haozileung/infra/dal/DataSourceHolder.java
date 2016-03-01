package com.haozileung.infra.dal;

public class DataSourceHolder {

	private final static ThreadLocal<String> name = new ThreadLocal<String>();

	public static String getName() {
		return name.get();
	}

	public static void setName(String name) {
		DataSourceHolder.name.set(name);
	}
}
