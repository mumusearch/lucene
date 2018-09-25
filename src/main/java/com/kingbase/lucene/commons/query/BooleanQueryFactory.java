package com.kingbase.lucene.commons.query;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.Query;

/**
 * BooleanQuery
 * 使用BooleanQuery可以将各种查询类型组合成复杂的查询方式
 * @author ganliang
 */
public class BooleanQueryFactory {

	/**
	 * 创建BooleanQuery
	 * @param querys 查询集合
	 * @param occurs occur集合
	 * @return
	 */
	public static Query createQuery(Query[] querys,Occur[] occurs){
		if(querys==null||querys.length==0||occurs==null||occurs.length==0||querys.length!=occurs.length){
			throw new IllegalArgumentException();
		}
		//构建BooleanQuery
		Builder builder = new BooleanQuery.Builder();
		for (int i = 0; i < querys.length; i++) {
			builder.add(querys[i], occurs[i]);
		}
		
		return builder.build();
	}
}
