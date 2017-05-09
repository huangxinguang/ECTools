package com.ectrip.core.lang;

import com.ectrip.lang.Console;
import org.junit.Test;



/**
 * 控制台单元测试
 * @author Looly
 *
 */
public class ConsoleTest {
	@Test
	public void logTest(){
		Console.log();
		
		String[] a = {"abc", "bcd", "def"};
		Console.log(a);
		
		Console.log("This is Console log for {}.", "test");
	}
	
	@Test
	public void errorTest(){
		Console.error();
		
		String[] a = {"abc", "bcd", "def"};
		Console.error(a);
		
		Console.error("This is Console error for {}.", "test");
	}
}
