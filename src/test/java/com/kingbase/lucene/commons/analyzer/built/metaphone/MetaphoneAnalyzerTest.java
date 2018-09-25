package com.kingbase.lucene.commons.analyzer.built.metaphone;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;

import com.kingbase.lucene.commons.analyzer.built.core.TokenAttributes;
import com.kingbase.lucene.commons.analyzer.built.metaphone.MetaphoneAnalyzer;

import junit.framework.TestCase;

public class MetaphoneAnalyzerTest extends TestCase {

	private String token = "The quick brown fox jumped over the lazzy dog";

	private String token2 = "The quick brown phox jumpd ovvar the lazzy dog";
	private Analyzer analyzer = new MetaphoneAnalyzer();
	TokenAttributes attributes = new TokenAttributes();

	public void testTokenTerm() throws IOException {
		String tokenTerm = attributes.tokenTerm(analyzer, "", token);
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}

	public void testTokenTerm2() throws IOException {
		String tokenTerm = attributes.tokenTerm(analyzer, "", token2);
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}
	
	public void testTokenTerm3() throws IOException {
		String tokenTerm = attributes.tokenTerm(analyzer, "", "love life live");
		System.out.println(analyzer.getClass().getSimpleName() + " " + tokenTerm);
	}
}
