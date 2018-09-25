package com.kingbase.lucene.commons.configuration;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lgan
 * 配置文件的初始化
 */
public class ConfigInitialization {

    private static final Logger log = Logger.getLogger(ConfigInitialization.class);
    //读取配置文件信息到内存中
    protected static Map<String, Map<String, Object>> mapData = new HashMap<String, Map<String, Object>>();
    protected static String WEB_INDEXES_DIRECTORY = null;

    /**
     * 获取配置文件的目录
     *
     * @return
     */
    public static InputStream getConfigStream() {
        return getConfigStream(Config.CONFIGLOCATION);
    }

    /**
     * 获取配置文件的目录
     *
     * @return
     */
    public static InputStream getConfigStream(String configName) {
        log.debug("加载lucen配置文件【" + Config.CONFIGLOCATION + "】");
        return ConfigInitialization.class.getResourceAsStream(configName);
    }

    /**
     * 加载配置文件
     */
    public static void load() {
        load(Config.CONFIGLOCATION);
    }

    /**
     * 加载配置文件
     *
     * @param configName 配置文件的路径
     */
    public static void load(String configName) {
        load(null, configName);
    }

    /**
     * 加载web配置文件
     *
     * @param directory
     * @param configName
     */
    public static void load(String directory, String configName) {
        if (StringUtils.isNotEmpty(directory)) {
            WEB_INDEXES_DIRECTORY = directory;
        }
        if (configName == null || "".equals(configName)) {
            configName = Config.CONFIGLOCATION;
        }

        InputStream stream = getConfigStream(configName);
        ParseConfig parseConfig = new ParseConfig(mapData);
        mapData = parseConfig.parse(stream);
    }

    /**
     * 加载配置信息
     *
     * @param configName 配置名称
     * @param configMap  配置map  {"version":"","directory":"","directory_type":"","analyzer":""}
     * @param fieldsMap  字段map  {"id":{"name":"","type":"","store":"","tokenized":"","indexed":""},...}
     *                   {lucene_db_base={analyzer=standardanalyzer, directory_type=fs, version=5.5.1, directory=e:\workspacelucene\lucene\src\main\webapp\indexes\lucene\db\base}, lucene_file_base={analyzer=standardanalyzer, directory_type=fs, fields={path={tokenized=true, indexed=docs_and_freqs_and_positions, name=路径, store=true, type=string}, filename={tokenized=true, indexed=docs_and_freqs_and_positions, name=文件名称, store=true, type=string}, size={tokenized=false, indexed=docs, name=大小, store=true, type=long}, id={tokenized=false, indexed=docs, name=主键, store=true, type=string}, attribute={tokenized=false, indexed=docs, name=属性, store=true, type=string}, type={tokenized=false, indexed=docs, name=类型, store=true, type=string}, content={tokenized=true, indexed=docs_and_freqs_and_positions, name=内容, store=true, type=text}}, version=5.5.1, directory=}}
     */
    public static void load(String configName, Map<String, Object> configMap, Map<String, Map<String, String>> fieldsMap) {
        if (configMap == null || fieldsMap == null) {
            throw new IllegalArgumentException("configMap、fieldsMap不能不为空");
        }
        configMap.put(Config.FIELDS, fieldsMap);
        mapData.put(configName, configMap);
    }

    //返回lucene配置信息
    public static Map<String, Map<String, Object>> getMapData() {
        if (mapData.size() == 0) {
            load();
        }
        return mapData;
    }
}
