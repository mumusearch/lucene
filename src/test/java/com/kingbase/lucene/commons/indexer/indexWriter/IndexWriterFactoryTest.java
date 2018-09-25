package com.kingbase.lucene.commons.indexer.indexWriter;

import org.apache.lucene.index.IndexWriter;

import junit.framework.TestCase;

public class IndexWriterFactoryTest extends TestCase{

	public void testCreate(){
		String configName="lucene_directory_memory";
		IndexWriter indexWriter = IndexWriterFactory.create(configName);
		
		System.out.println(indexWriter);
		IndexWriterFactory.close(configName);
	}
}
