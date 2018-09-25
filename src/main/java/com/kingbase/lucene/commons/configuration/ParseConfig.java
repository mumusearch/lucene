package com.kingbase.lucene.commons.configuration;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParseConfig {

    private static final Logger log = Logger.getLogger(ParseConfig.class);
    private Map<String, Map<String, Object>> mapData;

    public ParseConfig() {
    }

    public ParseConfig(Map<String, Map<String, Object>> mapData) {
        this.mapData = mapData;
    }

    //将配置文件读到内存

    /**
     * dom4j 解析xml文件
     *
     * @param stream
     */
    public Map<String, Map<String, Object>> parse(InputStream stream) {
        if (stream == null) {
            log.error("LUCENE配置文件找不到");
            throw new IllegalArgumentException("LUCENE配置文件找不到");
        }
        //获取xml的文档
        Document doc = getConfigDocument(stream);
        //遍历解析文档
        return analyzerConfigs(doc);
    }

    /**
     * 获取文档
     *
     * @return document
     */
    public Document getConfigDocument(InputStream stream) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(stream);
        } catch (Exception e) {
            log.error("解析xml出现异常!", e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
        return document;
    }

    /**
     * 遍历解析多个配置文件
     *
     * @param doc
     */
    @SuppressWarnings("unchecked")
    private Map<String, Map<String, Object>> analyzerConfigs(Document doc) {
        Element root = doc.getRootElement();//获取lucene节点
        List<Element> configs = root.elements();//获取每个config节点
        log.debug("读取LUCENE配置信息中.........");
        Iterator<Element> iterator = configs.iterator();
        while (iterator.hasNext()) {
            Element config = iterator.next();
            Attribute configNameAttr = config.attribute("name");
            if (configNameAttr == null || "".equals(configNameAttr)) {
                log.error("配置名称不能为空");
                throw new IllegalArgumentException("配置名称不能为空");
            }
            String configName = config.attribute("name").getValue();//获取配置的名称
            log.debug("读取配置【" + configName + "】中.........");

            Map<String, Object> configmap = analyzerConfig(config);
            mapData.put(configName.toLowerCase(), configmap);

            log.debug("读取配置【" + configName + "】结束.........");
            if (iterator.hasNext()) {
                log.debug("\n");
            }
        }
        log.debug("LUCENE配置信息读取完毕.........\n");
        return mapData;
    }

    /**
     * 解析一个配置
     *
     * @param config
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> analyzerConfig(Element config) {
        Map<String, Object> configmap = new HashMap<String, Object>();

        List<Element> elements = config.elements();
        for (Element element : elements) {
            String name = element.getName();
            String value = element.getTextTrim();

            //如果是字段集合配置
            if ("FIELDS".equalsIgnoreCase(name)) {
                log.debug("开始解析字段集合FIELDS");
                Map<String, Map<String, String>> fieldsMap = parseFields(element);
                log.debug("解析字段集合FIELDS结束");
                configmap.put(name.toLowerCase(), fieldsMap);//将字段的map放入配置map中
            } else {
                //将version directory存入configMap中
                configmap.put(name.toLowerCase(), value.toLowerCase());
                log.debug("配置信息 " + name.toLowerCase() + " " + value.toLowerCase());
            }
        }
        return configmap;
    }

    /**
     * 解析字段集合
     *
     * @param element
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Map<String, String>> parseFields(Element element) {
        Map<String, Map<String, String>> fieldsMap = new HashMap<String, Map<String, String>>();
        //获取所有的字段
        List<Element> fieldsElements = element.elements();
        //遍历字段集合 获取到每个字段
        for (Element fieldElement : fieldsElements) {
            //过滤无效配置
            Attribute nameAttribute = fieldElement.attribute("name");
            //如果字段没有配置字段名称 则过滤掉此属性
            if (nameAttribute == null) {
                break;
            }
            String fieldName = nameAttribute.getValue();
            Map<String, String> fieldMap = parseField(fieldElement);
            fieldsMap.put(fieldName.toLowerCase(), fieldMap);
            log.debug("字段" + fieldName + " " + fieldMap);
        }
        return fieldsMap;
    }

    /**
     * 解析一个字段
     *
     * @param fieldElement
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, String> parseField(Element fieldElement) {
        Map<String, String> fieldMap = new HashMap<String, String>();
        List<Element> attributeElements = fieldElement.elements();
        //获取到字段的所有属性
        for (Element attributeElement : attributeElements) {
            String attrName = attributeElement.getName();
            String attrValue = attributeElement.getTextTrim();
            fieldMap.put(attrName.toLowerCase(), attrValue.toLowerCase());
        }
        return fieldMap;
    }
}
