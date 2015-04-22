/**
 *
 */
package com.haozileung.test.infra.cache;

/**
 * 回调接口
 */
public interface ICacheInvoker<T> {

	T callback();

}
