package com.ectrip.core.util;

import java.util.Map;

import com.ectrip.util.ArrayUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * 数组工具单元测试
 * @author Looly
 *
 */
public class ArrayUtilTest {
	
	@Test
	public void isEmptyTest(){
		int[] a = {};
		int[] b = null;
		Assert.assertTrue(ArrayUtil.isEmpty(a));
		Assert.assertTrue(ArrayUtil.isEmpty(b));
	}
	
	@Test
	public void isNotEmptyTest(){
		int[] a = {1,2};
		Assert.assertTrue(ArrayUtil.isNotEmpty(a));
	}
	
	@Test
	public void newArrayTest(){
		String[] newArray = ArrayUtil.newArray(String.class, 3);
		Assert.assertEquals(3, newArray.length);
	}
	
	@Test
	public void cloneTest(){
		Integer[] b = {1,2,3};
		Integer[] cloneB = ArrayUtil.clone(b);
		Assert.assertArrayEquals(b, cloneB);
		
		int[] a = {1,2,3};
		int[] clone = ArrayUtil.clone(a);
		Assert.assertArrayEquals(a, clone);
	}

}
