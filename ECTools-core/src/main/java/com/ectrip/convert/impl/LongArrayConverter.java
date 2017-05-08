package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * long 类型数组转换器
 *
 */
public class LongArrayConverter extends AbstractConverter<long[]> {
	
	@Override
	protected long[] convertInternal(Object value) {
		final Long[] result = ConverterRegistry.getInstance().convert(Long[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
