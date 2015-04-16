/**
 * 
 */
package com.haozileung.test.domain.system.repository;

import com.haozileung.test.domain.system.Dictionary;
import com.haozileung.test.infra.QueryHelper;

/**
 * @author YamchaL
 *
 */
public final class DictionaryRepository {

	private final static class SingletonHolder {
		public final static DictionaryRepository instance = new DictionaryRepository();
	}

	public final static DictionaryRepository getInstance() {
		return SingletonHolder.instance;
	}

	public Dictionary load(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(Dictionary.TABLE);
		sb.append(" where id = ?");
		return QueryHelper.read(Dictionary.class, sb.toString(), id);
	}

	public void save(Dictionary dic) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(Dictionary.TABLE);
		sb.append(" (code,name,order,parentId,status) values (?,?,?,?,?)");
		QueryHelper.update(sb.toString(), dic.getCode(), dic.getName(),
				dic.getOrder(), dic.getParentId(), dic.getStataus());
	}

	public void update(Dictionary dic) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(Dictionary.TABLE);
		sb.append(" set code=?,name=?,order=?,parentId=?,status=? where id = ?");
		QueryHelper.update(sb.toString(), dic.getCode(), dic.getName(),
				dic.getOrder(), dic.getParentId(), dic.getStataus(),
				dic.getId());
	}

	public void delete(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(Dictionary.TABLE);
		sb.append(" where id = ?");
		QueryHelper.update(sb.toString(), id);
	}

}
