package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;

import java.util.TimeZone;

/**
 * TimeZone转换器
 *
 */
public class TimeZoneConverter extends AbstractConverter<TimeZone> {

	@Override
	protected TimeZone convertInternal(Object value) {
		return TimeZone.getTimeZone(convertToStr(value));
	}

}
