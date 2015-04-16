/**
 * 
 */
package com.haozileung.test.domain.system.repository;

import java.util.List;

import com.haozileung.test.domain.system.Dictionary;
import com.haozileung.test.infra.QueryHelper;
import com.haozileung.test.infra.cache.CacheHelper;
import com.haozileung.test.infra.cache.L1CacheManager;

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
		return QueryHelper.read_cache(Dictionary.class,
				Dictionary.class.getName(), id, sb.toString(), id);
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

	public void saveAll(List<Dictionary> dics) {
		if (dics != null && dics.size() > 0) {
			Object[][] params = new Object[dics.size()][];
			for (int i = 0; i < dics.size(); i++) {
				Dictionary dic = dics.get(i);
				params[i] = new Object[] { dic.getCode(), dic.getName(),
						dic.getOrder(), dic.getParentId(), dic.getStataus() };
			}
			StringBuilder sb = new StringBuilder();
			sb.append("insert into ");
			sb.append(Dictionary.TABLE);
			sb.append(" (code,name,order,parentId,status) values (?,?,?,?,?)");
			QueryHelper.batch(sb.toString(), params);
		}
	}

	public void updateAll(List<Dictionary> dics) {
		if (dics != null && dics.size() > 0) {
			Object[][] params = new Object[dics.size()][];
			for (int i = 0; i < dics.size(); i++) {
				Dictionary dic = dics.get(i);
				params[i] = new Object[] { dic.getCode(), dic.getName(),
						dic.getOrder(), dic.getParentId(), dic.getStataus(),
						dic.getId() };
			}
			StringBuilder sb = new StringBuilder();
			sb.append("update ");
			sb.append(Dictionary.TABLE);
			sb.append(" set code=?,name=?,order=?,parentId=?,status=? where id = ?");
			QueryHelper.batch(sb.toString(), params);
		}
	}
}
