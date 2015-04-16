/**
 * 
 */
package com.haozileung.test.domain.system.repository;

import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.QueryHelper;

/**
 * @author YamchaL
 *
 */
public final class UserRepository {

	private final static class SingletonHolder {
		public final static UserRepository instance = new UserRepository();
	}

	public final static UserRepository getInstance() {
		return SingletonHolder.instance;
	}

	public User load(Long uid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(User.TABLE);
		sb.append(" where id = ?");
		return QueryHelper.read(User.class, sb.toString(), uid);
	}

	public void save(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(User.TABLE);
		sb.append(" (username,password,email,status) values (?,?,?,?)");
		QueryHelper.update(sb.toString(), user.getUsername(),
				user.getPassword(), user.getEmail(), user.getStatus());
	}

	public void update(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(User.TABLE);
		sb.append(" set username=?,password=?,email=?,status=? where id = ?");
		QueryHelper.update(sb.toString(), user.getUsername(),
				user.getPassword(), user.getEmail(), user.getStatus(),
				user.getId());
	}

	public void delete(Long uid) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(User.TABLE);
		sb.append(" where id = ?");
		QueryHelper.update(sb.toString(), uid);
	}

}
