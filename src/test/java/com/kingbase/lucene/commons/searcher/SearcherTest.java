package com.kingbase.lucene.commons.searcher;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

public class SearcherTest extends TestCase{
	
	BaseSearcher searcher=new SimpleSearcher();
	//searcher=new HighLighterSearch();
	String configName="lucene_file_base";

	public void print(List<Map<String,Object>> data){
		System.out.println("搜索结果..................");
		if(data==null){
			return;
		}
		for (Map<String, Object> map : data) {
			System.out.println(map);
		}
		System.out.println("总查询数 "+data.size());
	}
}
