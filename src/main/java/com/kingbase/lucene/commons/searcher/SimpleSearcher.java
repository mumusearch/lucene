package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

import com.kingbase.lucene.commons.indexer.index.DocumentBuilding;
import com.kingbase.lucene.commons.indexer.indexSearcher.IndexSearcherFactory;

/**
 * 用于简单的查询
 * @author ganliang
 */
public class SimpleSearcher extends BaseSearcher{

	/**
	 * 执行搜索
	 * @param query 查询
	 * @param configName 配置名称
	 * @param count 
	 * @return
	 * @throws IOException 
	 */
	public List<Map<String, Object>> search(Query query, String configName,int count,String fieldName) throws IOException {
		// 获取indexSearch
		IndexSearcher indexSearcher = IndexSearcherFactory.create(configName);

		// 查询
		TopDocs topDocs = indexSearcher.search(query, count);

		DocumentBuilding documentBuilding = new DocumentBuilding();
		// 获取文档集合
		List<Document> docs = documentBuilding.handleTopDocs(indexSearcher, topDocs);
		// 解析文档
		return documentBuilding.parseDocuments(docs);
	}
}
