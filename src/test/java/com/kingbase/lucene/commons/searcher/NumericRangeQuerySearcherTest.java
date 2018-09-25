package com.kingbase.lucene.commons.searcher;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 数字范围
 * @author ganliang
 *
 */
public class NumericRangeQuerySearcherTest extends SearcherTest {

	/**
	 * 搜索 文件大小在  2048L ~ 2048L 的文档
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testNumericRangeQuery() throws IOException, ParseException{
		List<Map<String, Object>> numericRangeQuery = searcher.numericRangeQuery(configName, "SIZE", 0L, 204800L, Integer.MAX_VALUE);
		print(numericRangeQuery);
	}
	
	/**
	 * 搜索 文件大于 在 1024l*(1-BaseSearcher.FLOAT_RANGE) ~ 1024l*(1+BaseSearcher.FLOAT_RANGE) 的文档
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testNumericRangeQuery2() throws IOException, ParseException{
		List<Map<String, Object>> numericRangeQuery = searcher.numericRangeQuery(configName, "SIZE", 1024l, Integer.MAX_VALUE);
		print(numericRangeQuery);
	}
}
