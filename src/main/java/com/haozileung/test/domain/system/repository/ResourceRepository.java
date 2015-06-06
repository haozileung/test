/**
 *
 */
package com.haozileung.test.domain.system.repository;

import java.util.List;

import com.haozileung.infra.utils.QueryUtils;
import com.haozileung.test.domain.system.Resource;
import com.haozileung.test.domain.system.RoleResource;

/**
 * @author YamchaL
 */
public final class ResourceRepository {

	public final static ResourceRepository getInstance() {
		return SingletonHolder.instance;
	}

	public Resource load(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(Resource.TABLE);
		sb.append(" where id = ?");
		return QueryUtils.read(Resource.class, sb.toString(), id);
	}

	public void save(Resource res) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(Resource.TABLE);
		sb.append(" (code,name,type,url,groupId,status) values (?,?,?,?,?,?)");
		QueryUtils.update(sb.toString(), res.getCode(), res.getName(),
				res.getType(), res.getUrl(), res.getGroupId(), res.getStatus());
	}

	public void update(Resource res) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(Resource.TABLE);
		sb.append(" set code=?,name=?,type=?,url=?,groupId=?,status=? where id = ?");
		QueryUtils.update(sb.toString(), res.getCode(), res.getName(),
				res.getType(), res.getUrl(), res.getGroupId(), res.getStatus(),
				res.getId());
	}

	public void delete(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(Resource.TABLE);
		sb.append(" where id = ?");
		QueryUtils.update(sb.toString(), id);
		sb.delete(0, sb.length());
		sb.append("delete from ");
		sb.append(RoleResource.TABLE);
		sb.append(" where resourceId = ?");
		QueryUtils.update(sb.toString(), id);
	}

	public void saveAll(List<Resource> ress) {
		if (ress != null && ress.size() > 0) {
			Object[][] params = new Object[ress.size()][];
			for (int i = 0; i < ress.size(); i++) {
				Resource res = ress.get(i);
				params[i] = new Object[] { res.getCode(), res.getName(),
						res.getType(), res.getUrl(), res.getGroupId(),
						res.getStatus() };
			}
			StringBuilder sb = new StringBuilder();
			sb.append("insert into ");
			sb.append(Resource.TABLE);
			sb.append(" (code,name,type,url,groupId,status) values (?,?,?,?,?,?)");
			QueryUtils.batch(sb.toString(), params);
		}
	}

	public void updateAll(List<Resource> ress) {
		if (ress != null && ress.size() > 0) {
			Object[][] params = new Object[ress.size()][];
			for (int i = 0; i < ress.size(); i++) {
				Resource res = ress.get(i);
				params[i] = new Object[] { res.getCode(), res.getName(),
						res.getType(), res.getUrl(), res.getGroupId(),
						res.getStatus(), res.getId() };
			}
			StringBuilder sb = new StringBuilder();
			sb.append("update ");
			sb.append(Resource.TABLE);
			sb.append(" set code=?,name=?,type=?,url=?,groupId=?,status=? where id = ?");
			QueryUtils.batch(sb.toString(), params);
		}
	}

	private final static class SingletonHolder {
		public final static ResourceRepository instance = new ResourceRepository();
	}

}
