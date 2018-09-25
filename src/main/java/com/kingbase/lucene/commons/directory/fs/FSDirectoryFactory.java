package com.kingbase.lucene.commons.directory.fs;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * FSDirectory
 * @author ganliang
 */
public class FSDirectoryFactory {

	private static final Logger log=Logger.getLogger(FSDirectoryFactory.class);
	
	public static Directory create(String dir, String configName){
		try {
			File file=new File(dir);
			return FSDirectory.open(file.toPath());
		} catch (IOException e) {
			log.error("打开FSDirectory失败【"+dir+"】", e);
		}
		return null;
	}
}
