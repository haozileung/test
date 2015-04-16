/**
 * 
 */
package com.haozileung.test.infra.repository;

import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.QueryHelper;

/**
 * @author YamchaL
 *
 */
public class UserRepository {

	private static UserRepository repository;

	public static UserRepository getInstance() {
		if (null == repository) {
			repository = new UserRepository();
		}
		return repository;
	}

	public User load(Long uid) {
		return QueryHelper.read(User.class, "", uid);
	}

	public void save(User user) {
		QueryHelper.update("", user);
	}

}
