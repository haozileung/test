package com.haozileung.infra.web;

import com.google.inject.Injector;

public class Initializer {

	protected static Injector injector;

	public static Injector getInjector() {
		return injector;
	}

}
