package com.kingbase.lucene.commons.analyzer.built.synonym;

import java.io.IOException;

import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;

import junit.framework.TestCase;

public class SynonymAnalyzerTest extends TestCase{

	SynonymAnalyzer analyzer=new SynonymAnalyzer();
	private String token = "The fast quicked speedy jumped instant the lazzy dog";
	TokenAttributes attributes = new TokenAttributes();
	
	public void testSynonymAnalyzer() throws IOException {
		String tokenTerm = attributes.tokenTerm(analyzer, "", token);
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}

}
