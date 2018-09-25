package com.kingbase.lucene.commons.analyzer.chinese.contrib;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;

/**
 * CJKAnalyzer 中文分词器
 * @author ganliang
 * 
 *  <ol>
 *  <li>ChineseAnalyzer: 我－是－中－国－人</li>
 *  <li>CJKAnalyzer: 我是－是中－中国－国人</li>
 *  <li>SmartChineseAnalyzer: 我－是－中国－人</li>
 *  <ol>
 *
 */
public class CJKAnalyzerFactory{

	private CJKAnalyzerFactory(){
	}
	
	public static CJKAnalyzer instance(){
		CJKAnalyzer analyzer=new CJKAnalyzer();
		
		return analyzer;
	}
}
