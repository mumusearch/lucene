package com.kingbase.lucene.commons.analyzer.chinese.paoding;

import org.apache.lucene.analysis.Analyzer;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

/**
 * paoding 中文分词器
 * @author ganliang
 */
public class PaodingAnalyzerFactory {

	private static PaodingAnalyzer analyzer=null;
	
	private PaodingAnalyzerFactory(){
	}
	
	/**
	 * 单例 一个PaodingAnalyzer
	 * @return
	 */
	public synchronized static Analyzer instance(){
		if(analyzer==null){
			analyzer=new PaodingAnalyzer("classpath:paoding/paoding-analysis.properties");
		}
		return analyzer;
	}
	
}
