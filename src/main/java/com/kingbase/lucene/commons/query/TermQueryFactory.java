package com.kingbase.lucene.commons.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * TermQuery
 * TermQuery基于  域名/域值   来精准查询的
 * @author ganliang
 */
public class TermQueryFactory {

	/**
	 * 创建TermQuery
	 * @param fld 字段名称
	 * @param text 字段值
	 * @return
	 */
	public static Query createQuery(String fieldName,String fieldValue){
		
		//Term 最小的搜索片段
		return new TermQuery(new Term(fieldName, fieldValue));
	}
}
