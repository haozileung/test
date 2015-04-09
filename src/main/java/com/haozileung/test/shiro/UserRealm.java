package com.haozileung.test.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.QueryHelper;

public class UserRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException(
					"PrincipalCollection method argument cannot be null.");
		}
		String email = (String) principals.getPrimaryPrincipal();
		if (Strings.isNullOrEmpty(email)) {
			return null;
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> roleNames = QueryHelper
				.query(String.class,
						"SELECT r.roleName FROM t_role r LEFT JOIN t_user_role ur ON r.id = ur.roleId LEFT JOIN t_user u ON ur.userId = u.id AND u.status = 0 WHERE r.status = 0 AND u.email = ?",
						email);
		if (roleNames != null) {
			info.addRoles(roleNames);
			List<String> permissionCodes = QueryHelper
					.query(String.class,
							"SELECT p.permissionCode FROM t_permission p LEFT JOIN t_role_permission rp ON p.id = rp.permissionId LEFT JOIN t_role r ON rp.roleId = r.id AND r.status = 0 WHERE p.status = 0 AND r.roleName IN ('"
									+ Joiner.on("','").join(roleNames) + "')");
			if (permissionCodes != null) {
				info.addStringPermissions(permissionCodes);
			}
		}

		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String email = (String) token.getPrincipal();
		if (Strings.isNullOrEmpty(email)) {
			return null;
		}
		User u = QueryHelper.read(User.class,
				"SELECT * FROM t_user where email = ? LIMIT 1", email);
		if (u == null) {
			throw new UnknownAccountException(email + " is not found!");
		}
		if (u.getStatus() != null && u.getStatus().equals(1)) {
			throw new LockedAccountException(email + " is locked!");
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				u.getEmail(), u.getPassword(), getName());
		return authenticationInfo;
	}
}
