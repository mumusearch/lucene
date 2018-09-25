package com.kingbase.lucene.commons.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;

/**
 * 搜索以指定字符串开头的项的文档
 * @author ganliang
 */
public class PrefixQueryFactory {

	/**
	 * 获取前缀PrefixQuery
	 * @param fieldName
	 * @param prefix
	 * @return
	 */
	public static Query createQuery(String fieldName,String prefix){
		return new PrefixQuery(new Term(fieldName, prefix));
	}
}
