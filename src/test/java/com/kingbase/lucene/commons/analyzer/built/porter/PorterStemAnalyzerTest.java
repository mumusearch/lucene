package com.kingbase.lucene.commons.analyzer.built.porter;

import java.io.IOException;

import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;

import junit.framework.TestCase;

public class PorterStemAnalyzerTest extends TestCase{


	PorterStemAnalyzer analyzer=new PorterStemAnalyzer();
	
	private String token = "breathe breathes breathing breathed";
	TokenAttributes attributes = new TokenAttributes();
	
	public void testPorterStemAnalyzer() throws IOException {
		String tokenTerm = attributes.tokenTerm(analyzer, "", token);
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}
}
