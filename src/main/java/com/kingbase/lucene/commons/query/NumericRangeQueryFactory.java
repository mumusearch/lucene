package com.kingbase.lucene.commons.query;

import org.apache.lucene.rangetree.NumericRangeTreeQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;

/**
 * 使用NumericField对象来索引域，可以使用NumericRangeQuery在特定范围内搜索该域
 * @author ganliang
 */
public class NumericRangeQueryFactory {

	/**
	 * 匹配范围内的数字
	 * @param fieldName 域名
	 * @param minValue 最小值
	 * @param minInclusive 是否包括最小值
	 * @param maxValue 最大值
	 * @param maxInclusive 是否包括最大值
	 * @return
	 */
	public static Query createQuery(String fieldName,Object minValue,Object maxValue,boolean minInclusive,boolean maxInclusive){
		if(fieldName==null||"".equals(fieldName)||minValue==null||maxValue==null){
			throw new IllegalArgumentException();
		}
		
		Query query=null;
		// IntField
		if(minValue instanceof Integer&&maxValue instanceof Integer){
			query=NumericRangeQuery.newIntRange(fieldName, (int)minValue, (int)maxValue, minInclusive, maxInclusive);
		}
		// LongField
		else if(minValue instanceof Long&&maxValue instanceof Long){
			query=NumericRangeQuery.newLongRange(fieldName, (long)minValue, (long)maxValue, minInclusive, maxInclusive);
		}
		//FloatField.
		else if(minValue instanceof Float&&maxValue instanceof Float){
			query=NumericRangeQuery.newFloatRange(fieldName, (float)minValue, (float)maxValue, minInclusive, maxInclusive);
		}
		//DoubleField
		else if(minValue instanceof Double&&maxValue instanceof Double){
			query=NumericRangeQuery.newDoubleRange(fieldName, (double)minValue, (double)maxValue, minInclusive, maxInclusive);
		}else{
			throw new IllegalArgumentException("参数传递类型不明");
		}
		return query;
	}
	
	/**
	 * 匹配范围内的数字
	 * @param fieldName 域名
	 * @param minValue 最小值
	 * @param maxValue 最大值
	 * @return
	 */
	public static Query createQuery(String fieldName,Object minValue,Object maxValue){
		return createQuery(fieldName,minValue,maxValue,true,true);
	}
	
	/**
	 * NumericRangeTreeQuery
	 * @param fieldName 域名
	 * @param minValue 最小long值
	 * @param minInclusive 是否包括最小值
	 * @param maxValue 最大long值
	 * @param maxInclusive 是否包括最大值
	 * @return
	 */
	public static Query createNumericRangeTreeQuery(String fieldName,long minValue,boolean minInclusive,long maxValue,boolean maxInclusive){
		return new NumericRangeTreeQuery(fieldName, minValue, minInclusive, maxValue, maxInclusive);
	}
	
	/**
	 * NumericRangeTreeQuery
	 * 必须使用SortedNumericDocValuesField域
	 * @param fieldName 域名
	 * @param minValue 最小long值
	 * @param maxValue 最大long值
	 * @return
	 */
	public static Query createNumericRangeTreeQuery(String fieldName,long minValue,long maxValue){
		return createNumericRangeTreeQuery(fieldName, minValue, true, maxValue, true);
	}
}
