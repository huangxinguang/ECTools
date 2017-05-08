package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;

/**
 *布尔转换器
 *
 */
public class BooleanConverter extends AbstractConverter<Boolean> {

	@Override
	protected Boolean convertInternal(Object value) {
		if(boolean.class == value.getClass()){
			return Boolean.valueOf((Boolean)value);
		}
		String valueStr = convertToStr(value);
		return Boolean.valueOf(PrimitiveConverter.parseBoolean(valueStr));
	}

}
