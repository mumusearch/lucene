package com.kingbase.lucene.commons.analyzer.built.synonym;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.SynonymMap.Builder;
import org.apache.lucene.util.CharsRef;

/**
 * 构建 SynonymMap
 * @author ganliang
 */
public class SynonymMapFactory {
	
	private static final String SYNONYMPROPERTIES = "synonym.properties";//配置名称
	private static final String SEPARATOR = ",";//分隔符
	
	private static SynonymMap synonymMap = null;

	private SynonymMapFactory() {
	}

	/**
	 * 构建 同义词 Map
	 * @return
	 */
	public synchronized static final SynonymMap instance() {
		try {
			Builder builder = new SynonymMap.Builder(true);
			add(builder);
			synonymMap = builder.build();
			System.out.println(synonymMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return synonymMap;
	}

	/**
	 * 添加同义词
	 * @param builder
	 * @throws IOException
	 */
	public static void add(Builder builder) throws IOException {
		Properties properties = getSynonymProperties();
		//遍历 同义词配置文件
		for (Entry<Object, Object> entry : properties.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			if(key==null||"".equals(key.toString().trim())||value==null||"".equals(value.toString().trim())){
				continue;
			}
			String[] synonyms = value.toString().split(SEPARATOR);
			for (String synonym : synonyms) {
				builder.add(new CharsRef(synonym.trim()), new CharsRef(key.toString().trim()), true);
			}
		}
	}

	/**
	 * 获取同义词的配置文件
	 * @return
	 * @throws IOException
	 */
	public static Properties getSynonymProperties() throws IOException {
		Properties properties = new Properties();
		// 获取配置文件流
		Reader reader = getSynonymInputStream();
		properties.load(reader);
		reader.close();
		return properties;
	}

	/**
	 * 获取同义词的字符流
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Reader getSynonymInputStream() throws FileNotFoundException {
		// 获取包名
		String className = SynonymMapFactory.class.getPackage().getName();
		className = className.replace(".", File.separator);
        className=className+File.separator+SYNONYMPROPERTIES;
        
		// 获取classes目录
		String classesDir = SynonymMapFactory.class.getResource("/").getPath();
		try {
			classesDir=URLDecoder.decode(classesDir, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 排除测试环境
		classesDir = classesDir.replace("test-classes", "classes");
		
		return new FileReader(new File(classesDir,className));
	}
}
