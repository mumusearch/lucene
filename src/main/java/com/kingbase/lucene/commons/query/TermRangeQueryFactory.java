package com.kingbase.lucene.commons.query;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;

/**
 * 索引中的Term按照字典编排顺序进行排序
 * TermRangeQuery匹配一个Term从开始到结尾的域 例如 匹配从 sli ~ slider 的Term
 * @author ganliang
 */
public class TermRangeQueryFactory {

	/**
	 * 起始Term范围从lowerTerm ~ upperTerm 的域
	 * @param fieldName 域名
	 * @param lowerTerm 开始Term字符
	 * @param upperTerm 结束Term字符
	 * @param includeLower 是否包括开始lowerTerm
	 * @param includeUpper 是否包括结束upperTerm
	 * @return
	 */
	public static Query createQuery(String fieldName,String lowerTerm,String upperTerm,boolean includeLower, boolean includeUpper){
		if(lowerTerm==null||upperTerm==null){
			throw new IllegalArgumentException();
		}
		return new TermRangeQuery(fieldName, new BytesRef(lowerTerm), new BytesRef(upperTerm), includeLower, includeUpper);
	}
	
	/**
	 * 起始Term范围从lowerTerm ~ upperTerm 的域
	 * @param fieldName fieldName 域名
	 * @param lowerTerm lowerTerm 开始Term字符
	 * @param upperTerm upperTerm 结束Term字符
	 * @return
	 */
	public Query createQuery(String fieldName,String lowerTerm,String upperTerm){
		return createQuery(fieldName, lowerTerm, upperTerm, true, true);
	}
}
