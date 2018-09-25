package com.kingbase.lucene.file.service.impl;

import com.kingbase.lucene.commons.configuration.ConfigInitialization;
import com.kingbase.lucene.file.service.IconfigService;
import com.kingbase.lucene.file.utils.XmlUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import java.io.InputStream;
import java.util.Map;

public class ConfigServiceImpl implements IconfigService{
	private static final Logger log=Logger.getLogger(ConfigServiceImpl.class);
	@Override
	public boolean updateConfig(Map<String, String> map) {
		InputStream inputStream = ConfigInitialization.getConfigStream();
		XmlUtil xmlUtil=new XmlUtil();
		try {
			Document document = xmlUtil.getDocumnet(inputStream);
			return true;
		} catch (DocumentException e) {
			log.error("文档解析异常", e);
		}
		return false;
	}

}
