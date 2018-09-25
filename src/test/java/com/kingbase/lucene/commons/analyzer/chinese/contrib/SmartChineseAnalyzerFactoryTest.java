package com.kingbase.lucene.commons.analyzer.chinese.contrib;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;

import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;

import junit.framework.TestCase;

public class SmartChineseAnalyzerFactoryTest extends TestCase{

	Analyzer analyzer=SmartChineseAnalyzerFactory.instance();

	private String token = "中华人民共和国万岁";
	TokenAttributes attributes = new TokenAttributes();

	public void testSmartChineseAnalyzerFactory() throws IOException {
		String tokenTerm = attributes.tokenTerm(analyzer, "", token);
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}
}
