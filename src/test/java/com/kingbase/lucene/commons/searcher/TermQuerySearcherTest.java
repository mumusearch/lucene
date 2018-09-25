package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Term搜索
 * @author ganliang
 *
 */
public class TermQuerySearcherTest extends SearcherTest {

	public void testTermQuery() throws IOException{
		List<Map<String,Object>> data = searcher.termQuery(configName, "FILENAME", "web", 10);
		print(data);
	}
	
	public void testTermQuery2() throws IOException{
		List<Map<String,Object>> data = searcher.termQuery(configName, "ATTRIBUTE", "rwx", 10);
		print(data);
	}
	
	public void testTermQuery3() throws IOException{
		List<Map<String,Object>> data = searcher.termQuery(configName, "FILENAME", "log4j", 10);
		print(data);
	}
	
	public void testTermQuery4() throws IOException{
		List<Map<String,Object>> data = searcher.termQuery(configName, "PATH", "workspace", 10);
		print(data);
	}
	
	
	public void testTermQuery5() throws IOException{
		List<Map<String,Object>> data = searcher.termQuery(configName, "TYPE", "java", 10);
		print(data);
	}
	
}
