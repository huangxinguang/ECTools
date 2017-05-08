package com.ectrip.convert.impl;

import com.ectrip.convert.AbstractConverter;
import com.ectrip.date.DateUtil;
import com.ectrip.util.StringUtil;

import java.sql.Date;
import java.util.Calendar;

/**
 * SQL日期转换器
 *
 */
public class SqlDateConverter extends AbstractConverter<Date> {

	/** 日期格式化 */
	private String format;

	/**
	 * 获取日期格式
	 * 
	 * @return 设置日期格式
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * 设置日期格式
	 * 
	 * @param format 日期格式
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	protected Date convertInternal(Object value) {
		// Handle Calendar
		if (value instanceof Calendar) {
			return new Date(((Calendar) value).getTime().getTime());
		}

		// Handle Long
		if (value instanceof Long) {
			//此处使用自动拆装箱
			return new Date((Long)value);
		}

		final String valueStr = convertToStr(value);
		try {
			final long date = StringUtil.isBlank(format) ? DateUtil.parse(valueStr).getTime() : DateUtil.parse(valueStr, format).getTime();
			return new Date(date);
		} catch (Exception e) {
			// Ignore Exception
		}
		return null;
	}

}
