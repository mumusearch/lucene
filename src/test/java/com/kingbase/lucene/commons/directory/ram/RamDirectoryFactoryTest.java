package com.kingbase.lucene.commons.directory.ram;

import org.apache.lucene.store.Directory;

import junit.framework.TestCase;

public class RamDirectoryFactoryTest extends TestCase {

	public void testCreate() {
		String configName="";
		Directory directory = RamDirectoryFactory.create(configName);
		Directory directory2 = RamDirectoryFactory.create(configName);

		System.out.println(directory);
		RamDirectoryFactory.close(configName);

		System.out.println(directory2);
		RamDirectoryFactory.close(configName);
	}

}
