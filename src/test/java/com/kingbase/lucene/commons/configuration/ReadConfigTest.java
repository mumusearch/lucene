package com.kingbase.lucene.commons.configuration;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;

import junit.framework.TestCase;

public class ReadConfigTest extends TestCase{

	ReadConfig config=null;
	@Override
	protected void setUp() throws Exception {
		config=new ReadConfig();
	}

	public void testGetConfigName(){
		String configName = config.getConfigName();
		System.out.println(configName);
	}
	
	public void testGetFields(){
		Map<String, Map<String, String>> fields = config.getFields();
		System.out.println(fields);
	}
	
	public void testGetField(){
		Map<String, String> field = config.getField("coNTENTs");
		System.out.println(field);
	}
	
	public void testGetDirectory(){
		String directory = config.getDir();
		System.out.println(directory);
	}
	
	public void testGetVersion(){
		String version = config.getVersion();
		System.out.println(version);
	}
	
	public void testAnalyzer(){
		Analyzer analyzer = config.getAnalyzer();
		System.out.println(analyzer);
	}
}
