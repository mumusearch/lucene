package com.kingbase.lucene.commons.analyzer.chinese.contrib;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;

public class SmartChineseAnalyzerFactory {

	private SmartChineseAnalyzerFactory(){
	}
	
	public static SmartChineseAnalyzer instance(){
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		return analyzer;
	}
}
