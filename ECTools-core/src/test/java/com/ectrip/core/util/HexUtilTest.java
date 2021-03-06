package com.ectrip.core.util;

import com.ectrip.lang.Console;
import com.ectrip.util.CharsetUtil;
import com.ectrip.util.HexUtil;
import org.junit.Assert;
import org.junit.Test;


/**
 * HexUtil单元测试
 *
 */
public class HexUtilTest {
	
	@Test
	public void hexStrTest(){
		String str = "我是一个字符串";
		
		String hex = HexUtil.encodeHexStr(str, CharsetUtil.CHARSET_UTF_8);
		Console.log(hex);
		
		String decodedStr = HexUtil.decodeHexStr(hex);
		
		Assert.assertEquals(str, decodedStr);
	}
}
