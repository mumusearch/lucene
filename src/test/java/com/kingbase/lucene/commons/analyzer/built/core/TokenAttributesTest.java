package com.kingbase.lucene.commons.analyzer.built.core;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.kingbase.lucene.commons.analyzer.built.core.CustomizedSimpleAnalyzer;
import com.kingbase.lucene.commons.analyzer.built.core.CustomizedStandardAnalyzer;
import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;

import junit.framework.TestCase;

public class TokenAttributesTest extends TestCase{
	
	private String token="love cws LOVECWS,; LOVER sS the tha www.gl515xxx@163.com 123 567 890";
	
	private Analyzer[] analyzers=new Analyzer[]{
			new WhitespaceAnalyzer(),
			new SimpleAnalyzer(),
			new StopAnalyzer(),
			new StandardAnalyzer(),
			CustomizedStandardAnalyzer.create(),
			new CustomizedSimpleAnalyzer(false)
	};
	
	public void testTokenTerm() throws IOException{
		TokenAttributes attributes=new TokenAttributes();
		//遍历Analyzer
		for (Analyzer analyzer : analyzers) {
			String tokenTerm = attributes.tokenTerm(analyzer, "", token);
			System.out.println(analyzer.getClass().getSimpleName()+" "+tokenTerm);
		}
		
	}
	
	public void testToken() throws IOException{
		TokenAttributes attributes=new TokenAttributes();
		Analyzer analyzer=new WhitespaceAnalyzer();
		
		String tokenTerm = attributes.token(analyzer, "", token);
		System.out.println(tokenTerm);
	}

}
