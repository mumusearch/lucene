package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * 模糊查询
 * @author ganliang
 *
 */
public class FuzzyQuerySearcherTest extends SearcherTest{

	public void testFuzzyQuery() throws IOException, ParseException{
		List<Map<String, Object>> fuzzyQuery = searcher.fuzzyQuery(configName, "FILENAME", "pres", Integer.MAX_VALUE);
		print(fuzzyQuery);
	}
}
