package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * QueryParser
 * @author ganliang
 *
 */
public class QueryParserSearcherTest extends SearcherTest{

	public void testQueryParser() throws IOException, ParseException{
		List<Map<String,Object>> queryParse = searcher.queryParse(configName, "FILENAME", "elbow OR rtl", Integer.MAX_VALUE);
		print(queryParse);
		/**
		 * elbow-end
		 * window-header-default-collapsed-left-corners-rtl
		 */
	}
	
	public void testQueryParser2() throws IOException, ParseException{
		List<Map<String,Object>> queryParse = searcher.queryParse(configName, "FILENAME", "elbow AND rtl", Integer.MAX_VALUE);
		print(queryParse);
		/**
		 * 等同于  +elbow +rtl
		 * elbow-plus-nl-rtl
		 */
	}
	
	public void testQueryParser3() throws IOException, ParseException{
		List<Map<String,Object>> queryParse = searcher.queryParse(configName, "FILENAME", "PATH:elbow", Integer.MAX_VALUE);
		print(queryParse);
		/**
		 * 等同于  +elbow +rtl
		 * elbow-plus-nl-rtl
		 */
	}
}
