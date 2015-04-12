/**
 * 
 */
package com.haozileung.test.domain.system;


/**
 * @author YamchaL
 *
 */
public interface IUserRepository {

	public User load(Long uid);

	public void save(User user);
}
