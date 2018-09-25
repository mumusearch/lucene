package com.kingbase.lucene.file.utils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 * 
 * @author ganliang
 */
public class XmlUtil {

	private static final Logger log = Logger.getLogger(XmlUtil.class);

	/**
	 * 获取文档节点
	 * 
	 * @param stream
	 * @return
	 * @throws DocumentException
	 */
	public Document getDocumnet(InputStream stream) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		return saxReader.read(stream);
	}

	/**
	 * 找到字段集合元素
	 * 
	 * @param element
	 * @param string
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Element findFieldsElement(Element element, String configName) {
		List<Element> nodes = element.selectNodes("/lucene/config[@name='" + configName + "']/fields");
		if(nodes!=null&&nodes.size()>0){
			return nodes.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Element findFieldElement(Element fieldsElement,String fieldName){
		List<Element> nodes = fieldsElement.selectNodes("field[@name='"+fieldName+"']");
		if(nodes!=null&&nodes.size()>0){
			return nodes.get(0);
		}
		return null;
	}
	public void updateXML(InputStream stream, String configName, Map<String, Object> map) throws DocumentException {
		// 更新节点
		if (map == null) {
			log.debug("更新节点信息为空");
			return;
		}
		// 获取文档节点
		Document documnet = getDocumnet(stream);
		// 获取根节点
		Element rootElement = documnet.getRootElement();
		// 找配置节点
		Element fieldsElement = findFieldsElement(rootElement, configName);
		if (fieldsElement == null) {
			log.debug("找不到节点【" + configName + "】");
			return;
		}
		for (Entry<String, Object> entry : map.entrySet()) {

		}
	}
}
