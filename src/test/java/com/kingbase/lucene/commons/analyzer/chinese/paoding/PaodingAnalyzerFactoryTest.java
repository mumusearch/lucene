package com.kingbase.lucene.commons.analyzer.chinese.paoding;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;

import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;

import junit.framework.TestCase;

public class PaodingAnalyzerFactoryTest extends TestCase{
    private static final Analyzer analyzer=PaodingAnalyzerFactory.instance();
    
	public static void testPaodingAnalyzer() throws IOException{
		TokenAttributes attributes=new TokenAttributes();
		String string = attributes.token(analyzer, "", "中华人民共和国万岁");
		System.out.println(string);
	}
		
}
