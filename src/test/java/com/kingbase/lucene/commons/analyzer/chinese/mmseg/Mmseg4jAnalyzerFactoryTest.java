package com.kingbase.lucene.commons.analyzer.chinese.mmseg;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;

import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;
import com.kingbase.lucene.commons.analyzer.chinese.mmseg4j.Mmseg4jAnalyzerFactory;

import junit.framework.TestCase;

public class Mmseg4jAnalyzerFactoryTest extends TestCase{
	
	private String token = "中华人民共和国万岁";
	TokenAttributes attributes = new TokenAttributes();

	public void testSimpleAnalyzer() throws IOException {
		Analyzer analyzer= Mmseg4jAnalyzerFactory.simpleAnalyzer();
		
		String tokenTerm = attributes.tokenTerm(analyzer, "", token);
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}
	
	public void testComplexAnalyzer() throws IOException {
		Analyzer analyzer= Mmseg4jAnalyzerFactory.complexAnalyzer();
		
		String tokenTerm = attributes.tokenTerm(analyzer, "", token);
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}
}
