package com.kingbase.lucene.commons.searcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * term范围搜索
 * @author ganliang
 *
 */
public class TermRangeQuerySearcherTest extends SearcherTest {

	public void termRangeQuery() throws IOException{
		//slider
		List<Map<String,Object>> data = searcher.termRangeQuery(configName, "FILENAME", "slid", "slider", 10);
		print(data);
	}
	
	public void termRangeQuery2() throws IOException{
		//slider
		List<Map<String,Object>> data = searcher.termRangeQuery(configName, "PATH", "slid", "slider", 10);
		print(data);
	}
}
