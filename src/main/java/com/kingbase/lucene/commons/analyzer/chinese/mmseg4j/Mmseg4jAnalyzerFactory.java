package com.kingbase.lucene.commons.analyzer.chinese.mmseg4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis.ComplexAnalyzer;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.analysis.SimpleAnalyzer;

public class Mmseg4jAnalyzerFactory {
	
	private static final String path=getMmsegPath();//mmseg4j 配置路径
	
	private static SimpleAnalyzer simpleAnalyzer=null;
	private static ComplexAnalyzer complexAnalyzer=null;

	//单列 获取SimpleAnalyzer
	public synchronized static SimpleAnalyzer simpleAnalyzer(){
		if(simpleAnalyzer==null){
			simpleAnalyzer=new SimpleAnalyzer(path);
		}
		return simpleAnalyzer;
	}
	
	//单列 获取ComplexAnalyzer
	public synchronized static ComplexAnalyzer complexAnalyzer(){
		if(complexAnalyzer==null){
			complexAnalyzer=new ComplexAnalyzer(path);
		}
		return complexAnalyzer;
	}
	
	/**
	 * 获取配置路径
	 * @return
	 */
	private static String getMmsegPath(){
		String path = Mmseg4jAnalyzerFactory.class.getResource("/mmseg").getPath();
		try {
			path=URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
	}
}
