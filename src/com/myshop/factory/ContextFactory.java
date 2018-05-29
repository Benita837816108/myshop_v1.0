package com.myshop.factory;

import java.io.InputStream;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ContextFactory {
	public static Object getInstance(String id) throws Exception{
		//解析xml文件,context.xml文件
		SAXReader reader = new SAXReader();
		//直接使用类加载器将xml文件转换成流再传入到read方法中
		ClassLoader classLoader = ContextFactory.class.getClassLoader();
		InputStream in = classLoader.getResourceAsStream("context.xml");
		Document document = reader.read(in);
		//要根据id的值找到对应的context标签中calss属性
		//使用xpath查找
		Element element=(Element) document.selectSingleNode("/content/context[@id='"+id+"']");
		//通过变迁找到该标签中的class属性
		Attribute attribute = element.attribute("class");
		//取出class属性的属性值---->就是要创建对象的类的全限定名
		String value = attribute.getValue();
		Class<?> clazz = Class.forName(value);
		 Object object = clazz.newInstance();
		 return object;
		
	}
}
