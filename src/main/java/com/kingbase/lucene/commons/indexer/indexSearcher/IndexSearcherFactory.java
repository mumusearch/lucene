package com.kingbase.lucene.commons.indexer.indexSearcher;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;

import com.kingbase.lucene.commons.indexer.indexReader.IndexReaderFactory;

public class IndexSearcherFactory {

	/**
	 * 获取一个IndexSearcher
	 * @param configName
	 * @return
	 */
	public static IndexSearcher create(String configName){
		IndexReader indexReader = IndexReaderFactory.create(configName);
		return new IndexSearcher(indexReader);
	}

}
