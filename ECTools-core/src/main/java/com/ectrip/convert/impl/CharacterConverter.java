package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.util.StringUtil;

/**
 * 字符转换器
 *
 */
public class CharacterConverter extends AbstractConverter<Character> {

	@Override
	protected Character convertInternal(Object value) {
		if(char.class == value.getClass()){
			return Character.valueOf((Character) value);
		}else{
			final String valueStr = convertToStr(value);
			if (StringUtil.isNotBlank(valueStr)) {
				try {
					return Character.valueOf(valueStr.charAt(0));
				} catch (Exception e) {
					//Ignore Exception
				}
			}
		}
		return null;
	}

}
