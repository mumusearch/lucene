package com.kingbase.lucene.commons.indexer.indexSearcher;

import org.apache.lucene.search.IndexSearcher;

import junit.framework.TestCase;

public class IndexSearcherFactoryTest extends TestCase{

	public void testCreate(){
		String configName="lucene_directory_memory";
		IndexSearcher indexSearcher = IndexSearcherFactory.create(configName);
		System.out.println(indexSearcher);
	}
}
