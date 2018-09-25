package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * 短语查询
 * @author ganliang
 *
 */
public class PhraseQuerySearcherTest extends SearcherTest{
	
	/**
	 * 查询 在FILENAME域中 Term(ext) 和 Term(all)紧邻着的文档
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testPhraseQuery() throws IOException, ParseException{
		List<Map<String,Object>> phraseQuery = searcher.phraseQuery(configName, "FILENAME", new String[]{"ext","all"}, 0 ,Integer.MAX_VALUE);
		print(phraseQuery);
		/**
		 * ext-all
		 */
	}
	
	/**
	 * 查询 在FILENAME域中 Term(ext) 和 Term(all)相距slop的文档
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testPhraseQuery2() throws IOException, ParseException{
		List<Map<String,Object>> phraseQuery = searcher.phraseQuery(configName, "FILENAME", new String[]{"ext","all"}, 2 ,Integer.MAX_VALUE);
		print(phraseQuery);
		/**
		 * ext-all
		 * ext-all-rtl
		 * ext-theme-classic-all-rtl
		 */
	}
	
	/**
	 * 查询 在FILENAME域中 Term(ext) 和 Term(all)相距slop的文档
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testPhraseQuery3() throws IOException, ParseException{
		List<Map<String,Object>> phraseQuery = searcher.phraseQuery(configName, "FILENAME", new String[]{"all","ext"}, 2 ,Integer.MAX_VALUE);
		print(phraseQuery);
		/**
		 * ext-all
		 */
	}
}
