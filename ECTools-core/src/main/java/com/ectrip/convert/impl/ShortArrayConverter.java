package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * short 类型数组转换器
 *
 */
public class ShortArrayConverter extends AbstractConverter<short[]> {
	
	@Override
	protected short[] convertInternal(Object value) {
		final Short[] result = ConverterRegistry.getInstance().convert(Short[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
