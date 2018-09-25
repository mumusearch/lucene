package com.kingbase.lucene.commons.analyzer.chinese.ik.cfg;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

public class DefaultConfig
        implements Configuration {
    private static final String PATH_DIC_MAIN = "/ikdic/main2012.dic";
    private static final String PATH_DIC_QUANTIFIER = "/ikdic/quantifier.dic";
    private static final String FILE_NAME = "/ikdic/IKAnalyzer.cfg.xml";
    private static final String EXT_DICT = "/ikdic/ext_dict";
    private static final String EXT_STOP = "/ikdic/ext_stopwords";
    private Properties props;
    private boolean useSmart;

    public static Configuration getInstance() {
        return new DefaultConfig();
    }

    private DefaultConfig() {
        this.props = new Properties();

        InputStream input = getClass().getClassLoader().getResourceAsStream(FILE_NAME);

        if (input != null)
            try {
                this.props.loadFromXML(input);
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public boolean useSmart() {
        return this.useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public String getMainDictionary() {
        return PATH_DIC_MAIN;
    }

    public String getQuantifierDicionary() {
        return PATH_DIC_QUANTIFIER;
    }

    public List<String> getExtDictionarys() {
        List extDictFiles = new ArrayList(2);
        String extDictCfg = this.props.getProperty("ext_dict");
        if (extDictCfg != null) {
            String[] filePaths = extDictCfg.split(";");
            if (filePaths != null) {
                for (String filePath : filePaths) {
                    if ((filePath != null) && (!"".equals(filePath.trim()))) {
                        extDictFiles.add(filePath.trim());
                    }
                }
            }
        }
        return extDictFiles;
    }

    public List<String> getExtStopWordDictionarys() {
        List extStopWordDictFiles = new ArrayList(2);
        String extStopWordDictCfg = this.props.getProperty("ext_stopwords");
        if (extStopWordDictCfg != null) {
            String[] filePaths = extStopWordDictCfg.split(";");
            if (filePaths != null) {
                for (String filePath : filePaths) {
                    if ((filePath != null) && (!"".equals(filePath.trim()))) {
                        extStopWordDictFiles.add(filePath.trim());
                    }
                }
            }
        }
        return extStopWordDictFiles;
    }
}