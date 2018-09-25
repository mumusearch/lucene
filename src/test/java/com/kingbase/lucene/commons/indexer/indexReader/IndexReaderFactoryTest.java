package com.kingbase.lucene.commons.indexer.indexReader;

import org.apache.lucene.index.IndexReader;

import junit.framework.TestCase;

public class IndexReaderFactoryTest extends TestCase{

	public void testCreate(){
		String configName="lucene_directory_memory";
		IndexReader indexReader = IndexReaderFactory.create(configName);
		System.out.println(indexReader);
	}
}
