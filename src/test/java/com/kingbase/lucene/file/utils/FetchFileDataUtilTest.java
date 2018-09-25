package com.kingbase.lucene.file.utils;

import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class FetchFileDataUtilTest extends TestCase{

	String configName="lucene_file_base";
	
	public void testFetchFileDatas() throws FileNotFoundException{
		FetchFileDataUtil dataUtil=new FetchFileDataUtil();
		List<Map<String,Object>> fetchFileDatas = dataUtil.fetchFileDatas(configName, "G:/workspace");
		for (Map<String, Object> map : fetchFileDatas) {
			System.out.println(map);
		}
	}
	
}
