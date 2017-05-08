package com.ectrip.core.date;

import com.ectrip.date.BetweenFormater;
import com.ectrip.date.DateUtil;
import org.junit.Assert;
import org.junit.Test;

public class BetweenFormaterTest {
	
	@Test
	public void formatTest(){
		long betweenMs = DateUtil.betweenMs(DateUtil.parse("2017-01-01 22:59:59"), DateUtil.parse("2017-01-02 23:59:58"));
		BetweenFormater formater = new BetweenFormater(betweenMs, BetweenFormater.Level.MILLSECOND, 1);
		Assert.assertEquals(formater.toString(), "1天");
	}
}
