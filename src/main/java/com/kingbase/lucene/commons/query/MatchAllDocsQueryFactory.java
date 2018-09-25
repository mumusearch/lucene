package com.kingbase.lucene.commons.query;

import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;

/**
 * 匹配索引的所有查询
 * @author ganliang
 */
public class MatchAllDocsQueryFactory {

	/**
	 * 获取MatchAllDocsQuery
	 * @return
	 */
	public static Query createQuery(){
		return new MatchAllDocsQuery();
	}
}
