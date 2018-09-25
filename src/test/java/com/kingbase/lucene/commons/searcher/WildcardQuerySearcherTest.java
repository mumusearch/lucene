package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 通配符搜索
 * @author ganliang
 *
 */
public class WildcardQuerySearcherTest extends SearcherTest{

	public void testWildcardQuery() throws IOException{
		List<Map<String,Object>> wildcardQuery = searcher.wildcardQuery(configName, "FILENAME", "*de*", Integer.MAX_VALUE);
		print(wildcardQuery);
	}
	
	public void testWildcardQuery2() throws IOException{
		List<Map<String,Object>> wildcardQuery = searcher.wildcardQuery(configName, "FILENAME", "*de?", Integer.MAX_VALUE);
		print(wildcardQuery);
	}
}
