package com.kingbase.lucene.commons.analyzer.built.core;

import java.io.IOException;

import com.kingbase.lucene.commons.analyzer.built.core.CustomizedStandardAnalyzer;

import junit.framework.TestCase;

public class StandardAnalyzerTest extends TestCase{

	public void testStandardAnalyzer() throws IOException{
		
		System.out.println(CustomizedStandardAnalyzer.create());
	}
}
