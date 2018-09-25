package com.kingbase.lucene.commons.analyzer.built.synonym;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import junit.framework.TestCase;

public class SynonymMapFactoryTest extends TestCase{
	
	public void testGetSynonymInputStream() throws IOException{
		Reader synonymInputStream = SynonymMapFactory.getSynonymInputStream();
		System.out.println(synonymInputStream);
		synonymInputStream.close();
	}
	
	public void testGetSynonymProperties() throws IOException{
		Properties synonymProperties = SynonymMapFactory.getSynonymProperties();
		System.out.println(synonymProperties);
	}
	
	public void testInstance() throws IOException{
		SynonymMapFactory.instance();
	}
}
