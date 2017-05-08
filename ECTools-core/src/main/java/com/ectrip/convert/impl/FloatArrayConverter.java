package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * float 类型数组转换器
 *
 */
public class FloatArrayConverter extends AbstractConverter<float[]> {
	
	@Override
	protected float[] convertInternal(Object value) {
		final Float[] result = ConverterRegistry.getInstance().convert(Float[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
