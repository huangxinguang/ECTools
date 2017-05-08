package com.ectrip.convert.impl;


import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * double 类型数组转换器
 *
 */
public class DoubleArrayConverter extends AbstractConverter<double[]> {
	
	@Override
	protected double[] convertInternal(Object value) {
		final Double[] result = ConverterRegistry.getInstance().convert(Double[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
