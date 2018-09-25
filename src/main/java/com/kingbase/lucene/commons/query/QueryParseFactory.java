package com.kingbase.lucene.commons.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

/**
 * QueryParse
 * QueryParse表达式
 * 使用方法
 * java                                                   默认域包含java
 * java junit                                             默认域包含java或者junit
 * java OR junit                                          同上
 * +java +junit                                           默认域包含java和junit
 * java AND junit                                         同上
 * title:ant                                              title域包含ant
 * title:extrem -subject:sports                           title域包含extrem 并且subject域不包含sports
 * title:extrem  AND NOT subject:sports                   同上
 * (java OR junit) AND metho                              默认域包含metho并且包含java或者junit
 * title:"lucene in action"                               title域为lucene in action
 * title:"lucene action" ~5                               title域中lucene和action的距离小于5 使用PhraseQuery短语查询
 * java*                                                  默认域以java开头的文档                         使用PrefixQuery前缀查询
 * java~                                                  默认域包含与java相近的文档 如lava 使用的FuzzyQuery模糊查询
 * 在QueryParser需要转义的字符\                               \ + - ! ( ) : ^ ]  { } ~ * ?
 * @author ganliang
 */
public class QueryParseFactory {

	private static final List<String> operators=new ArrayList<String>();
	static{
		operators.add("+");
		operators.add(" AND ");
		operators.add(" OR ");
		operators.add(" NOT ");
		operators.add("-");
		operators.add(":");
		operators.add("~");
		operators.add("*");
		operators.add(" ");
	}
	/**
	 * 创建查询
	 * @param fieldName 字段名称
	 * @param inputValue 输入的表达式
	 * @param analyzer 对inputValue分解的解析器
	 * @return
	 * @throws ParseException
	 */
	public static Query createQuery(String fieldName,String inputValue,Analyzer analyzer) throws ParseException{
		QueryParser queryParser = new QueryParser(fieldName, analyzer);
		return queryParser.parse(inputValue);
	}
	
	/**
	 * 判断是否是queryParse查询
	 * @param fieldValue
	 * @return
	 */
	public static boolean isQueryParse(String fieldValue){
		if(fieldValue==null||"".equals(fieldValue)){
			return false;
		}
		for (String operator : operators) {
			if(fieldValue.toUpperCase().contains(operator)){
				return true;
			}
		}
		return false;
	}
}
