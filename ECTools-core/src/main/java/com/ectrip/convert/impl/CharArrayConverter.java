package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * char类型数组转换器
 * @author Looly
 *
 */
public class CharArrayConverter extends AbstractConverter<char[]> {
	
	@Override
	protected char[] convertInternal(Object value) {
		final Character[] result = ConverterRegistry.getInstance().convert(Character[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
