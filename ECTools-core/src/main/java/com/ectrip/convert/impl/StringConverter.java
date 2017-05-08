package com.ectrip.convert.impl;


import com.ectrip.convert.AbstractConverter;

/**
 * 字符串转换器
 *
 */
public class StringConverter extends AbstractConverter<String> {

	@Override
	protected String convertInternal(Object value) {
		return convertToStr(value);
	}

}
