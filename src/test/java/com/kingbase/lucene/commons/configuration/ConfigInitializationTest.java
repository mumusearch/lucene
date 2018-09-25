package com.kingbase.lucene.commons.configuration;

import junit.framework.TestCase;

public class ConfigInitializationTest extends TestCase{

	public void testLoad(){
		ConfigInitialization.load();
		
		System.out.println(ConfigInitialization.mapData);
	}
}
