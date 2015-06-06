/**
 *
 */
package com.haozileung.test.domain.system.repository;

import java.util.List;

import com.haozileung.infra.utils.QueryUtils;
import com.haozileung.test.domain.system.Dictionary;

/**
 * @author YamchaL
 */
public final class DictionaryRepository {

	public final static DictionaryRepository getInstance() {
		return SingletonHolder.instance;
	}

	public Dictionary load(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(Dictionary.TABLE);
		sb.append(" where id = ?");
		return QueryUtils.read_cache(Dictionary.class,
				Dictionary.class.getName(), id, sb.toString(), id);
	}

	public void save(Dictionary dic) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(Dictionary.TABLE);
		sb.append(" (code,name,order,parentId,status) values (?,?,?,?,?)");
		QueryUtils.update(sb.toString(), dic.getCode(), dic.getName(),
				dic.getOrder(), dic.getParentId(), dic.getStataus());
	}

	public void update(Dictionary dic) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(Dictionary.TABLE);
		sb.append(" set code=?,name=?,order=?,parentId=?,status=? where id = ?");
		QueryUtils.update(sb.toString(), dic.getCode(), dic.getName(),
				dic.getOrder(), dic.getParentId(), dic.getStataus(),
				dic.getId());
	}

	public void delete(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(Dictionary.TABLE);
		sb.append(" where id = ?");
		QueryUtils.update(sb.toString(), id);
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
			QueryUtils.batch(sb.toString(), params);
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
			QueryUtils.batch(sb.toString(), params);
		}
	}

	private final static class SingletonHolder {
		public final static DictionaryRepository instance = new DictionaryRepository();
	}
}
