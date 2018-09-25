package com.kingbase.lucene.commons.directory;

import org.apache.lucene.store.Directory;

import com.kingbase.lucene.commons.directory.fs.FSDirectoryFactory;
import com.kingbase.lucene.commons.directory.ram.RamDirectoryFactory;

public class DirectoryConfig {

	/**
	 * @param directoryType 索引类型 
	 * @param dir 索引目录
	 * @param configName 配置名称
	 * @return
	 */
	public Directory directory(String directoryType, String dir, String configName) {
		if(directoryType==null||"".equals(directoryType)){
			return RamDirectoryFactory.create(configName);
		}
		
		Directory directory=null;
		switch (directoryType) {
		case "RAM":
			directory=RamDirectoryFactory.create(configName);
			break;
		case "FS":
			directory=FSDirectoryFactory.create(dir, configName);
			break;
		default:
			directory=FSDirectoryFactory.create(dir, configName);
			break;
		}
		return directory;
	}

}
