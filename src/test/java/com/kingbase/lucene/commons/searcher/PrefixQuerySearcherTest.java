package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * 前缀搜索
 * @author ganliang
 *
 */
public class PrefixQuerySearcherTest extends SearcherTest{

	public void testPrefixQuery() throws IOException, ParseException{
		List<Map<String,Object>> prefixQuery = searcher.prefixQuery(configName, "FILENAME", "de", Integer.MAX_VALUE);
		print(prefixQuery);
	}
	
}
