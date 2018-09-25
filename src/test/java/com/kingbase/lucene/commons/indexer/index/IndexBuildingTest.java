package com.kingbase.lucene.commons.indexer.index;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class IndexBuildingTest extends TestCase{

	public void testBuild(){
		IndexBuilding building=new IndexBuilding();
		
		String configName="lucene_directory_memory";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", "1");
		map.put("fileName", null);
		map.put("size", "1");
		map.put("content", new StringReader("11111111111111111111111111111111111111"));
		map.put("attribute", "rw");
		map.put("path", "c:/user/local/1.txt");
		
		building.build(map, configName);
	}
}
