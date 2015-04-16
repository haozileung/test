/**
 * 
 */
package com.haozileung.test.domain.system.repository;

import com.haozileung.test.domain.system.Resource;
import com.haozileung.test.infra.QueryHelper;

/**
 * @author YamchaL
 *
 */
public final class ResourceRepository {

	private final static class SingletonHolder {
		public final static ResourceRepository instance = new ResourceRepository();
	}

	public final static ResourceRepository getInstance() {
		return SingletonHolder.instance;
	}

	public Resource load(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(Resource.TABLE);
		sb.append(" where id = ?");
		return QueryHelper.read(Resource.class, sb.toString(), id);
	}

	public void save(Resource res) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(Resource.TABLE);
		sb.append(" (code,name,type,url,groupId,status) values (?,?,?,?,?,?)");
		QueryHelper.update(sb.toString(), res.getCode(), res.getName(),
				res.getType(), res.getUrl(), res.getGroupId(), res.getStatus());
	}

	public void update(Resource res) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(Resource.TABLE);
		sb.append(" set code=?,name=?,type=?,url=?,groupId=?,status=? where id = ?");
		QueryHelper.update(sb.toString(), res.getCode(), res.getName(),
				res.getType(), res.getUrl(), res.getGroupId(), res.getStatus(),
				res.getId());
	}

	public void delete(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(Resource.TABLE);
		sb.append(" where id = ?");
		QueryHelper.update(sb.toString(), id);
	}

}
