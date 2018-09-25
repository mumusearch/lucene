package com.kingbase.lucene.commons.analyzer.built.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.WordlistLoader;

/**
 * 使用StandardTokenizer将字符流转化为语汇单元
 * StandardFilter 
 * LowerCaseFilter 将语汇单元转化为小写
 * StopFilter 去除停分词的语汇单元
 * @author ganliang
 *
 */
public class CustomizedStandardAnalyzer{

	private static final String STOP_WORDS_PROPERTIES="stopwords.properties";
	
	private CustomizedStandardAnalyzer() {
	}
   
	/**
	 * 创建一个 自定义停分词的StandardAnalyzer
	 * @return
	 * @throws IOException 
	 */
	public static StandardAnalyzer create(){
		//获取包名
		String packageName = CustomizedStandardAnalyzer.class.getPackage().getName();
		packageName=packageName.replace(".",File.separator);
		
		//获取classes目录
		String classesDir = CustomizedStandardAnalyzer.class.getResource("/").getPath();
		//排除测试环境
		classesDir=classesDir.replace("test-classes", "classes");
		
		//从字符流中获取字符集合
		CharArraySet wordSet;
		try {
			//获取停分词
			File stopWordsFile=new File(classesDir+packageName,STOP_WORDS_PROPERTIES);
			FileReader reader = new FileReader(stopWordsFile);
			
			wordSet = WordlistLoader.getWordSet(reader);
			System.out.println(wordSet);
			return new StandardAnalyzer(wordSet);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
