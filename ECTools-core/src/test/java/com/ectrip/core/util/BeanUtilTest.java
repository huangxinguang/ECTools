package com.ectrip.core.util;

import java.util.HashMap;

import com.ectrip.util.BeanUtil;
import com.ectrip.util.CollectionUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Bean工具单元测试
 */
public class BeanUtilTest {
	
	@Test
	public void isBeanTest(){
		
		//HashMap不包含setXXX方法，不是bean
		boolean isBean = BeanUtil.isBean(HashMap.class);
		Assert.assertFalse(isBean);
	}
	
	@Test
	public void fillBeanTest(){
		Person person = BeanUtil.fillBean(new Person(), new BeanUtil.ValueProvider<String>(){


			public Object value(String key, Class<?> valueType) {
				switch (key) {
					case "name":
						return "张三";
					case "age":
						return 18;
				}
				return null;
			}

			public boolean containsKey(String key) {
				//总是存在key
				return true;
			}
			
		}, BeanUtil.CopyOptions.create());
		
		Assert.assertEquals(person.getName(), "张三");
		Assert.assertEquals(person.getAge(), 18);
	}


	
	//-----------------------------------------------------------------------------------------------------------------
	public static class SubPerson extends Person{
		
	}
	
	public static class Person{
		private String name;
		private int age;
		private String openid;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
	}
}
