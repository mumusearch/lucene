package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * 匹配所有的文档
 * @author ganliang
 *
 */
public class MatchAllDocsQuerySearcherTest extends SearcherTest{

	public void testMatchAllDocsQuery() throws IOException, ParseException{
		List<Map<String, Object>> matchAllDocsQuery = searcher.matchAllDocsQuery(configName);
		print(matchAllDocsQuery);
	}
}
