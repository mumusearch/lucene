package com.kingbase.lucene.commons.directory.ram;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * RAMDirectory 将索引信息保存到内存中
 * 优点：创建索引和搜索比较快
 * 缺点：一次性，无法保存
 * @author ganliang
 */
public class RamDirectoryFactory {
	
	private static final Logger log=Logger.getLogger(RamDirectoryFactory.class);
	
	private static final ConcurrentHashMap<Object, Directory> directorys=new ConcurrentHashMap<Object, Directory>();
	
	private RamDirectoryFactory() {
	}

	/**
	 * 创建一个空的RamDirectory
	 * @param configName 
	 * @return
	 */
	public static Directory create(String configName){
		Directory directory = directorys.get(configName);
		if(directory==null){
			directory=new RAMDirectory();
			directorys.put(configName, directory);
		}	
		return directory;
	}
	
	/**
	 * 关闭
	 * @param configName 配置名称
	 */
	public static void close(String configName){
		try {
			Directory directory = directorys.get(configName);
			if(directory!=null){
				directory.close();
				directory=null;
			}
		} catch (IOException e) {
			log.error("关闭RAMDirectory失败", e);
		}
	}
	/**
	 * 关闭
	 */
	public static void clear(){
		try {
			Set<Entry<Object,Directory>> entrySet = directorys.entrySet();
			for (Entry<Object, Directory> entry : entrySet) {
				Directory directory = entry.getValue();
				directory.close();
				directory=null;
			}
			directorys.clear();//清空map
		} catch (IOException e) {
			log.error("关闭RAMDirectory失败", e);
		}
	}
}
