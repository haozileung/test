/**
 * 
 */
package com.haozileung.test.domain.system.repository;

import com.haozileung.test.domain.system.Role;
import com.haozileung.test.infra.QueryHelper;

/**
 * @author YamchaL
 *
 */
public final class RoleRepository {

	private final static class SingletonHolder {
		public final static RoleRepository instance = new RoleRepository();
	}

	public final static RoleRepository getInstance() {
		return SingletonHolder.instance;
	}

	public Role load(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(Role.TABLE);
		sb.append(" where id = ?");
		return QueryHelper.read(Role.class, sb.toString(), id);
	}

	public void save(Role role) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(Role.TABLE);
		sb.append(" (name,status) values (?,?)");
		QueryHelper.update(sb.toString(), role.getName(), role.getStatus());
	}

	public void update(Role role) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(Role.TABLE);
		sb.append(" set name=?,status=? where id = ?");
		QueryHelper.update(sb.toString(), role.getName(), role.getStatus(),
				role.getId());
	}

	public void delete(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(Role.TABLE);
		sb.append(" where id = ?");
		QueryHelper.update(sb.toString(), id);
	}

}
