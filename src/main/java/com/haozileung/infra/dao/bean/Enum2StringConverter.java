package com.haozileung.infra.dao.bean;

import com.haozileung.infra.dao.enums.IEnum;

/**
 * IEnum到String转换器
 */
public class Enum2StringConverter implements TypeConverter {

	@Override
	public Object convert(Class<?> sourceClass, Class<?> targetClass,
			Object value) {

		if (value == null) {
			return null;
		}
		if (targetClass.equals(String.class)
				&& IEnum.class.isAssignableFrom(sourceClass)) {
			return ((IEnum) value).getCode();
		}
		return value;
	}
}
