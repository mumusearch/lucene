package com.kingbase.lucene.file.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.kingbase.lucene.commons.configuration.ConfigInitialization;
import com.kingbase.lucene.commons.configuration.ParseConfig;

import junit.framework.TestCase;

public class XmlUtilTest extends TestCase{

	public void testXpath(){
		InputStream stream = ConfigInitialization.getConfigStream();
		ParseConfig config=new ParseConfig();
		Document document = config.getConfigDocument(stream);
		Element rootElement = document.getRootElement();
		
		String configName="lucene_file_base";
		//List nodes = rootElement.selectNodes("/lucene/config[name='"+configName+"']/fields");
		List<Element> nodes = rootElement.selectNodes("/lucene/config[@name='"+configName+"']/fields");
		System.out.println(nodes.get(0));
		Element el = nodes.get(0);
		System.out.println(el.elements().size());
		String fieldName="fileName";
		nodes = el.selectNodes("fields/field[@name='"+fieldName+"']");
		System.out.println(nodes.size());
	}
}
