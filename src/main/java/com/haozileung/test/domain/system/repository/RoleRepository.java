/**
 *
 */
package com.haozileung.test.domain.system.repository;

import com.haozileung.test.domain.system.Role;
import com.haozileung.test.domain.system.RoleResource;
import com.haozileung.test.domain.system.UserRole;
import com.haozileung.test.infra.QueryHelper;

import java.util.List;

/**
 * @author YamchaL
 */
public final class RoleRepository {

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
        sb.delete(0, sb.length());
        sb.append("delete from ");
        sb.append(RoleResource.TABLE);
        sb.append(" where roleId = ?");
        QueryHelper.update(sb.toString(), id);
        sb.delete(0, sb.length());
        sb.append("delete from ");
        sb.append(UserRole.TABLE);
        sb.append(" where roleId = ?");
        QueryHelper.update(sb.toString(), id);
    }

    public void saveAll(List<Role> roles) {
        if (roles != null && roles.size() > 0) {
            Object[][] params = new Object[roles.size()][];
            for (int i = 0; i < roles.size(); i++) {
                Role role = roles.get(i);
                params[i] = new Object[]{role.getName(), role.getStatus()};
            }
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ");
            sb.append(Role.TABLE);
            sb.append(" (name,status) values (?,?)");
            QueryHelper.batch(sb.toString(), params);
        }
    }

    public void updateAll(List<Role> roles) {
        if (roles != null && roles.size() > 0) {
            Object[][] params = new Object[roles.size()][];
            for (int i = 0; i < roles.size(); i++) {
                Role role = roles.get(i);
                params[i] = new Object[]{role.getName(), role.getStatus(),
                        role.getId()};
            }
            StringBuilder sb = new StringBuilder();
            sb.append("update ");
            sb.append(Role.TABLE);
            sb.append(" set name=?,status=? where id = ?");
            QueryHelper.batch(sb.toString(), params);
        }
    }

    public void setPermission(Long roleId, List<Long> permissionIds) {
        if (permissionIds != null && permissionIds.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from ");
            sb.append(UserRole.TABLE);
            sb.append(" where userId = ?");
            QueryHelper.update(sb.toString(), roleId);
            Object[][] params = new Object[permissionIds.size()][];
            for (int i = 0; i < permissionIds.size(); i++) {
                Long permissionId = permissionIds.get(i);
                params[i] = new Object[]{roleId, permissionId};
            }
            sb.delete(0, sb.length());
            sb.append("INSERT INTO ");
            sb.append(RoleResource.TABLE);
            sb.append(" (roleId, resourceId) values (?, ?)");
            QueryHelper.batch(sb.toString(), params);
        }
    }

    public List<RoleResource> getPermissions(Long roleId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(RoleResource.TABLE);
        sb.append(" where roleId = ?");
        return QueryHelper.query(RoleResource.class, sb.toString(), roleId);

    }

    private final static class SingletonHolder {
        public final static RoleRepository instance = new RoleRepository();
    }

}
