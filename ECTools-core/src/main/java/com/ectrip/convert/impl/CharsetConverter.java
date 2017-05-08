package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * 编码对象转换器
 *
 */
public class CharsetConverter extends AbstractConverter<Charset> {

	@Override
	protected Charset convertInternal(Object value) {
		return CharsetUtil.charset(convertToStr(value));
	}

}
