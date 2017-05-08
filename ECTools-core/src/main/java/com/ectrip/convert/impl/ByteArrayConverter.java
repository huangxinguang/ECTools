package com.ectrip.convert.impl;


import com.ectrip.convert.AbstractConverter;
import com.ectrip.convert.ConverterRegistry;
import com.ectrip.util.ArrayUtil;

/**
 * byte 类型数组转换器
 *
 */
public class ByteArrayConverter extends AbstractConverter<byte[]> {
	
	@Override
	protected byte[] convertInternal(Object value) {
		final Byte[] result = ConverterRegistry.getInstance().convert(Byte[].class, value);
		return ArrayUtil.unWrap(result);
	}

}
