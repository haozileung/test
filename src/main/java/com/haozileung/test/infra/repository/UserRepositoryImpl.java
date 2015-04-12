/**
 * 
 */
package com.haozileung.test.infra.repository;

import com.haozileung.test.domain.system.IUserRepository;
import com.haozileung.test.domain.system.User;

/**
 * @author YamchaL
 *
 */
public class UserRepositoryImpl implements IUserRepository {

	/* (non-Javadoc)
	 * @see com.haozileung.test.domain.system.IUserRepository#load(java.lang.Long)
	 */
	@Override
	public User load(Long uid) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.haozileung.test.domain.system.IUserRepository#save(com.haozileung.test.domain.system.User)
	 */
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub

	}

}
