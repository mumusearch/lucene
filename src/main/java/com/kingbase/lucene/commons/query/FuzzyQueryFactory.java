package com.kingbase.lucene.commons.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;

/**
 * 模糊查询FuzzyQuery
 * FuzzyQuery用于匹配与指定项相似的项
 * @author ganliang
 */
public class FuzzyQueryFactory {

	/**
	 * 获取FuzzyQuery
	 * @param fieldName 域名称
	 * @param fuzzy 模糊值
	 * @return
	 */
	public static Query createQuery(String fieldName,String fuzzy){ 
		return new FuzzyQuery(new Term(fieldName, fuzzy));
	}
}
