package com.kingbase.lucene.file.utils;

import junit.framework.TestCase;

import java.io.IOException;

public class IndexUtilTest extends TestCase{

	String configName="lucene_file_base";
	String directory="G:/workspace/lgan";

	public void testCreateIndex() throws IOException{
		IndexUtil indexUtil=new IndexUtil();

		indexUtil.createIndex(configName, directory);
	}

	public void testClear() throws IOException{
		IndexUtil indexUtil=new IndexUtil();

		indexUtil.clear(configName);
	}

}
