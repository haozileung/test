package com.haozileung.infra.dao.persistence;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.haozileung.infra.dao.annotation.Column;
import com.haozileung.infra.dao.annotation.ID;
import com.haozileung.infra.dao.annotation.Table;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 默认名称处理handler
 */
public class DefaultNameHandler implements NameHandler {

	private static Map<Class<?>, Map<String, String>> nameMap = Maps.newConcurrentMap();

	/**
	 * 根据实体名获取表名
	 *
	 * @param entityClass
	 * @return
	 */
	@Override
	public String getTableName(Class<?> entityClass) {
		if (!nameMap.containsKey(entityClass)) {
			init(entityClass);
		}
		return nameMap.get(entityClass).get("_tableName_");
	}

	private void init(Class<?> entityClass) {
		String tableName = null;
		String keyName = "id";
		// 获得实体的字段(包括父类）
		Map<String, String> map = Maps.newConcurrentMap();
		Field[] fields = entityClass.getDeclaredFields();
		if (entityClass.getAnnotation(Table.class) != null) {
			// 初始化表名
			tableName = entityClass.getAnnotation(Table.class).value();
		}
		if (Strings.isNullOrEmpty(tableName)) {
			tableName = entityClass.getSimpleName();
		}
		map.put("_tableName_", tableName);
		map.put("_keyName_", keyName);
		for (Field f : fields) {
			if (f.getAnnotation(Column.class) != null) {
				// 拿到这个实体的主键名
				if (f.getAnnotation(ID.class) != null) {
					map.put("_keyName_", f.getAnnotation(Column.class).value());
				} else {
					map.put(f.getName(), f.getAnnotation(Column.class).value());
				}
			} else {
				// 拿到这个实体的主键名
				if (f.getAnnotation(ID.class) != null) {
					map.put("_keyName_", f.getName());
				} else {
					map.put(f.getName(), f.getName());
				}
			}
		}
		nameMap.put(entityClass, map);
	}

	/**
	 * 根据表名获取主键名
	 *
	 * @param entityClass
	 * @return
	 */
	@Override
	public String getPKName(Class<?> entityClass) {
		if (!nameMap.containsKey(entityClass)) {
			init(entityClass);
		}
		return nameMap.get(entityClass).get("_keyName_");
	}

	/**
	 * 根据属性名获取列名
	 *
	 * @param fieldName
	 * @return
	 */
	@Override
	public String getColumnName(Class<?> entityClass, String fieldName) {
		if (!nameMap.containsKey(entityClass)) {
			init(entityClass);
		}
		return nameMap.get(entityClass).get(fieldName);
	}

	/**
	 * 根据实体名获取主键值 自增类主键数据库直接返回null即可
	 *
	 * @param entityClass
	 *            the entity class
	 * @param dialect
	 *            the dialect
	 * @return pK value
	 */
	@Override
	public String getPKValue(Class<?> entityClass, String dialect) {
		if (StringUtils.equalsIgnoreCase(dialect, "oracle")) {
			// 获取序列就可以了，默认seq_加上表名为序列名
			String tableName = this.getTableName(entityClass);
			return String.format("SEQ_%s.NEXTVAL", tableName);
		}
		return null;
	}
}
