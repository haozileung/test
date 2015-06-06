/**
 *
 */
package com.haozileung.test.domain.system.repository;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.utils.QueryUtils;
import com.haozileung.test.domain.system.User;
import com.haozileung.test.domain.system.UserRole;

import java.util.List;

/**
 * @author YamchaL
 */
public final class UserRepository {

	public final static UserRepository getInstance() {
		return SingletonHolder.instance;
	}

	public User load(Long uid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(User.TABLE);
		sb.append(" where id = ?");
		return QueryUtils.read(User.class, sb.toString(), uid);
	}

	public void save(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(User.TABLE);
		sb.append(" (name,password,email,status) values (?,?,?,?)");
		QueryUtils.update(sb.toString(), user.getName(),
				user.getPassword(), user.getEmail(), user.getStatus());
	}

	public void update(User user) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(User.TABLE);
		sb.append(" set name=?,password=?,email=?,status=? where id = ?");
		QueryUtils.update(sb.toString(), user.getName(),
				user.getPassword(), user.getEmail(), user.getStatus(),
				user.getId());

	}

	public void delete(Long uid) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(User.TABLE);
		sb.append(" where id = ?");
		QueryUtils.update(sb.toString(), uid);
		sb.delete(0, sb.length());
		sb.append("delete from ");
		sb.append(UserRole.TABLE);
		sb.append(" where userId = ?");
		QueryUtils.update(sb.toString(), uid);
	}

	public void saveAll(List<User> users) {
		if (users != null && users.size() > 0) {
			Object[][] params = new Object[users.size()][];
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				params[i] = new Object[]{user.getName(),
						user.getPassword(), user.getEmail(), user.getStatus() };
			}
			StringBuilder sb = new StringBuilder();
			sb.append("insert into ");
			sb.append(User.TABLE);
			sb.append(" (name,password,email,status) values (?,?,?,?)");
			QueryUtils.batch(sb.toString(), params);
		}
	}

	public void updateAll(List<User> users) {
		if (users != null && users.size() > 0) {
			Object[][] params = new Object[users.size()][];
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				params[i] = new Object[]{user.getName(),
						user.getPassword(), user.getEmail(), user.getStatus(),
						user.getId() };
			}
			StringBuilder sb = new StringBuilder();
			sb.append("update ");
			sb.append(User.TABLE);
			sb.append(" set name=?,password=?,email=?,status=? where id = ?");
			QueryUtils.batch(sb.toString(), params);
			for (User user : users) {
				CacheHelper.updateNow(User.class.getName(), user.getId(), user);
			}
		}
	}

	public void setRole(Long uid, List<Long> roleIds) {
		if (roleIds != null && roleIds.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("delete from ");
			sb.append(UserRole.TABLE);
			sb.append(" where userId = ?");
			QueryUtils.update(sb.toString(), uid);
			Object[][] params = new Object[roleIds.size()][];
			for (int i = 0; i < roleIds.size(); i++) {
				Long roleId = roleIds.get(i);
				params[i] = new Object[] { uid, roleId };
			}
			sb.delete(0, sb.length());
			sb.append("INSERT INTO ");
			sb.append(UserRole.TABLE);
			sb.append(" (userId, roleId) values (?, ?)");
			QueryUtils.batch(sb.toString(), params);
		}
	}

	public List<UserRole> getRoles(Long uid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(UserRole.TABLE);
		sb.append(" where userId = ?");
		return QueryUtils.query(UserRole.class, sb.toString(), uid);

	}

	private final static class SingletonHolder {
		public final static UserRepository instance = new UserRepository();
	}

}
