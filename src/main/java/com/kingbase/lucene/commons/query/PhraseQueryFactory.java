package com.kingbase.lucene.commons.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PhraseQuery.Builder;
import org.apache.lucene.search.Query;

/**
 * PhraseQuery 短语查询
 * PhraseQuery会根据位置信息定莫个距离范围内的项所对应的文档
 * @author ganliang
 */
public class PhraseQueryFactory {

	/**
	 * 
	 * @param fieldName 域名
	 * @param phrases 短语数组
	 * @param slop 两个项之间最大的距离
	 * @return
	 */
	public static Query createQuery(String fieldName,String[] phrases,int slop){
		Builder builder = new PhraseQuery.Builder();
		builder.setSlop(slop);
		
		for (String phrase : phrases) {
			builder.add(new Term(fieldName, phrase));
		}
		
		return builder.build();
	}
}
