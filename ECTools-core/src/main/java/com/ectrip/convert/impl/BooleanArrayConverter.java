package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * boolean类型数组转换器
 * @author Looly
 *
 */
public class BooleanArrayConverter extends AbstractConverter<boolean[]> {
	
	@Override
	protected boolean[] convertInternal(Object value) {
		final Boolean[] result = ConverterRegistry.getInstance().convert(Boolean[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
