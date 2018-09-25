package com.kingbase.lucene.commons.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;

/**
 * 通配符查询
 * 用法
 * *                           代表0个或者多个字符
 * ?                           代表0个或者1个字符
 * warn 使用通配符查询可能会降低系统性能
 * @author ganliang
 */
public class WildcardQueryFactory {

	/**
	 * 获取通配符查询
	 * @param fieldName 域名
	 * @param wildcard 通配符值
	 * @return
	 */
	public static Query createQuery(String fieldName,String wildcard){
		return new WildcardQuery(new Term(fieldName,wildcard));
	}
}
