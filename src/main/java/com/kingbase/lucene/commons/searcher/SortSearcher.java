package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.SortField.Type;

import com.kingbase.lucene.commons.configuration.ReadConfig;
import com.kingbase.lucene.commons.indexer.index.DocumentBuilding;
import com.kingbase.lucene.commons.indexer.indexSearcher.IndexSearcherFactory;

/**
 * 按照一个字段的评分来排序
 * @author ganliang
 */
public class SortSearcher extends BaseSearcher {

	@Override
	public List<Map<String, Object>> search(Query query, String configName, int count, String fieldName)
			throws IOException {
		//获取域名的类型
		ReadConfig config=new ReadConfig(configName);
		Type type = config.getType(fieldName);
		
		//获取排序的列
		Sort sort = new Sort(new SortField(fieldName, type));
		// 获取indexSearch
		IndexSearcher indexSearcher = IndexSearcherFactory.create(configName);

		// 查询
		TopDocs topDocs = indexSearcher.search(query, count, sort);

		DocumentBuilding documentBuilding = new DocumentBuilding();
		// 获取文档集合
		List<Document> docs = documentBuilding.handleTopDocs(indexSearcher, topDocs);
		// 解析文档
		return documentBuilding.parseDocuments(docs);
	}

}
