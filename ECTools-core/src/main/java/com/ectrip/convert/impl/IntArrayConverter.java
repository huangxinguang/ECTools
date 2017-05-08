package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * int 类型数组转换器
 *
 */
public class IntArrayConverter extends AbstractConverter<int[]> {
	
	@Override
	protected int[] convertInternal(Object value) {
		final Integer[] result = ConverterRegistry.getInstance().convert(Integer[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
