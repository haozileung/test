/**
 * 
 */
package com.haozileung.infra.utils;

import com.google.inject.AbstractModule;

/**
 * @author Efun
 *
 */
public class UtilModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(ThreadExecution.class).toInstance(new ThreadExecution());
	}

}
