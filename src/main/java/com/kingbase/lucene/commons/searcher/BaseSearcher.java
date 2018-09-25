package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.Query;

import com.kingbase.lucene.commons.configuration.ReadConfig;
import com.kingbase.lucene.commons.query.BooleanQueryFactory;
import com.kingbase.lucene.commons.query.FuzzyQueryFactory;
import com.kingbase.lucene.commons.query.MatchAllDocsQueryFactory;
import com.kingbase.lucene.commons.query.NumericRangeQueryFactory;
import com.kingbase.lucene.commons.query.PhraseQueryFactory;
import com.kingbase.lucene.commons.query.PrefixQueryFactory;
import com.kingbase.lucene.commons.query.QueryParseFactory;
import com.kingbase.lucene.commons.query.TermQueryFactory;
import com.kingbase.lucene.commons.query.TermRangeQueryFactory;
import com.kingbase.lucene.commons.query.WildcardQueryFactory;

/**
 * 查询基类
 * @author ganliang
 */
public abstract class BaseSearcher {
	
	//数字查询的浮动范围
	private static final double FLOAT_RANGE=0.2;
	/**
	 * TermQuery
	 * @param configName
	 * @param fieldName
	 * @param fieldValue
	 * @param count
	 * @throws IOException
	 */
	public List<Map<String, Object>> termQuery(String configName, String fieldName, String fieldValue, int count)
			throws IOException {
		// 获取查询
		Query query = TermQueryFactory.createQuery(fieldName, fieldValue);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * TermQuery
	 * @param configName
	 * @param fieldName
	 * @param fieldValue
	 * @param count
	 * @throws IOException
	 */
	public List<Map<String, Object>> termRangeQuery(String configName, String fieldName,String lowerTerm,String upperTerm,boolean includeLower, boolean includeUpper, int count)
			throws IOException {
		// 获取查询
		Query query = TermRangeQueryFactory.createQuery(fieldName, lowerTerm, upperTerm, includeLower, includeUpper);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * termRangeQuery
	 * @param configName
	 * @param fieldName
	 * @param lowerTerm
	 * @param upperTerm
	 * @param count
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, Object>> termRangeQuery(String configName, String fieldName,String lowerTerm,String upperTerm, int count)
			throws IOException {
		return termRangeQuery(configName, fieldName, lowerTerm, upperTerm, true, true, count);
	}


	/**
	 * wildcardQuery
	 * @param configName
	 * @param fieldName
	 * @param fieldValue
	 * @param count
	 * @throws IOException
	 */
	public List<Map<String, Object>> wildcardQuery(String configName, String fieldName,String wildcard,int count)
			throws IOException {
		// 获取查询
		Query query = WildcardQueryFactory.createQuery(fieldName, wildcard);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * QueryParse
	 * @param configName
	 * @param fieldName
	 * @param inputValue
	 * @param analyzer
	 * @param count
	 * @throws IOException
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> queryParse(String configName, String fieldName,String inputValue,Analyzer analyzer,int count)
			throws IOException, ParseException {
		// 获取查询
		Query query = QueryParseFactory.createQuery(fieldName, inputValue,analyzer);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * QueryParse
	 * @param configName
	 * @param fieldName
	 * @param inputValue
	 * @param count
	 * @throws IOException
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> queryParse(String configName, String fieldName,String inputValue,int count)
			throws IOException, ParseException {
		//使用配置的analyzer
		ReadConfig config=new ReadConfig(configName);
		Analyzer analyzer = config.getAnalyzer();
		// 获取查询
		return queryParse(configName, fieldName, inputValue, analyzer, count);
	}
	
	/**
	 * prefixQuery
	 * @param configName
	 * @param fieldName
	 * @param prefix
	 * @param count
	 * @throws IOException
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> prefixQuery(String configName, String fieldName,String prefix,int count)
			throws IOException, ParseException {
		// 获取查询
		Query query = PrefixQueryFactory.createQuery(fieldName, prefix);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * phraseQuery短语查询
	 * @param configName
	 * @param fieldName
	 * @param phrases
	 * @param slop
	 * @throws IOException
	 * @throws ParseException 
	 */
	public List<Map<String, Object>> phraseQuery(String configName, String fieldName,String[] phrases,int slop,int count)
			throws IOException, ParseException {
		// 获取查询
		Query query = PhraseQueryFactory.createQuery(fieldName,phrases,slop);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * numericRangeQuery数字范围查询
	 * @param configName
	 * @param fieldName
	 * @param minValue
	 * @param maxValue
	 * @param minInclusive
	 * @param maxInclusive
	 * @param count
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Map<String, Object>> numericRangeQuery(String configName, String fieldName,Object minValue,Object maxValue,boolean minInclusive,boolean maxInclusive,int count)
			throws IOException, ParseException {
		// 获取查询
		Query query = NumericRangeQueryFactory.createQuery(fieldName,minValue,maxValue,minInclusive,maxInclusive);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * numericRangeQuery数字范围查询
	 * @param configName
	 * @param fieldName
	 * @param minValue
	 * @param maxValue
	 * @param count
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Map<String, Object>> numericRangeQuery(String configName, String fieldName,Object minValue,Object maxValue,int count)
			throws IOException, ParseException {
		return numericRangeQuery(configName, fieldName, minValue, maxValue, true, true, count);
	}
	
	/**
	 * numericRangeQuery数字范围查询
	 * @param configName
	 * @param fieldName
	 * @param number 数字
	 * @param count
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Map<String, Object>> numericRangeQuery(String configName, String fieldName,Object number,int count)
			throws IOException, ParseException {
		if(number==null){
			throw new IllegalArgumentException();
		}
		Object minValue=null;
		Object maxValue=null;
		if(number instanceof Integer){
			int num = Integer.parseInt(number.toString());
			minValue=(int)(num*(1-FLOAT_RANGE));
			maxValue=(int)(num*(1+FLOAT_RANGE));
		}else if(number instanceof Long){
			long num = Long.parseLong(number.toString());
			minValue=(long)(num*(1-FLOAT_RANGE));
			maxValue=(long)(num*(1+FLOAT_RANGE));
		}else if(number instanceof Float){
			float num = Float.parseFloat(number.toString());
			minValue=(float)(num*(1-FLOAT_RANGE));
			maxValue=(float)(num*(1+FLOAT_RANGE));
		}else if(number instanceof Double){
			double num = Double.parseDouble(number.toString());
			minValue=(double)(num*(1-FLOAT_RANGE));
			maxValue=(double)(num*(1+FLOAT_RANGE));
		}else{
			throw new IllegalArgumentException();
		}
		return numericRangeQuery(configName, fieldName, minValue, maxValue, true, true, count);
	}
	
	/**
	 * 查询所有的文档
	 * @param configName
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Map<String, Object>> matchAllDocsQuery(String configName)
			throws IOException, ParseException {
		// 获取查询
		Query query = MatchAllDocsQueryFactory.createQuery();
		return search(query, configName, Integer.MAX_VALUE,null);
	}
	
	/**
	 * 查询所有的文档
	 * @param configName
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Map<String, Object>> fuzzyQuery(String configName,String fieldName,String fuzzy,int count)
			throws IOException, ParseException {
		// 获取查询
		Query query = FuzzyQueryFactory.createQuery(fieldName, fuzzy);
		return search(query, configName, count,fieldName);
	}
	
	/**
	 * 联合查询
	 * @param querys
	 * @param occurs
	 * @param fieldName
	 * @param count
	 * @return
	 * @throws IOException 
	 */
	public List<Map<String, Object>> booleanQuery(Query[] querys,Occur[] occurs,String configName,String fieldName,int count) throws IOException{
		Query booleanQuery = BooleanQueryFactory.createQuery(querys, occurs);
		return search(booleanQuery, configName, count,fieldName);
	}
	
	public abstract List<Map<String, Object>> search(Query query, String configName, int count,String fieldName) throws IOException;
}
