package com.haozileung.infra.dao.bean;

import com.haozileung.infra.dao.enums.IEnum;

/**
 * String 到 IEnum 转换器
 * <p>
 */
public class String2EnumConverter implements TypeConverter {

	/**
	 * Convert the specified input object into an output object of the specified
	 * type.
	 *
	 * @param sourceClass
	 *            the source class
	 * @param targetClass
	 *            Data type to which this value should be converted
	 * @param value
	 *            The input value to be converted
	 * @return The converted value
	 */
	@Override
	public Object convert(Class<?> sourceClass, Class<?> targetClass,
			Object value) {
		if (value == null) {
			return null;
		}
		IEnum[] iEnums = null;
		if (sourceClass.equals(String.class)
				&& IEnum.class.isAssignableFrom(targetClass)) {
			iEnums = (IEnum[]) targetClass.getEnumConstants();
			for (IEnum iEnum : iEnums) {
				if (iEnum.getCode().equals(String.valueOf(value))) {
					return iEnum;
				}
			}
		}

		throw new IllegalArgumentException("Parameter is not legitimate");
	}
}
