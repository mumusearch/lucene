package com.kingbase.lucene.commons.indexer.indexReader;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;

import com.kingbase.lucene.commons.configuration.ReadConfig;

/**
 * indexReader 
 * @author ganliang
 *
 */
public class IndexReaderFactory {
	
	private static final Logger log = Logger.getLogger(IndexReaderFactory.class);
	private static final ConcurrentHashMap<Object, DirectoryReader> indexReaders = new ConcurrentHashMap<Object, DirectoryReader>();
	
	/**
	 * 获取indexReader
	 * @param configName
	 * @return
	 */
	public static IndexReader create(String configName){
		DirectoryReader indexReader=null;
		try {
			indexReader = indexReaders.get(configName);
			if(indexReader==null){
				ReadConfig config = new ReadConfig(configName);
				Directory directory = config.getDirectory();
				indexReader = DirectoryReader.open(directory);
				
				indexReaders.put(configName, indexReader);
			}else{
				DirectoryReader directoryReader = DirectoryReader.openIfChanged(indexReader);
				if(directoryReader!=null){//index has changed 
					indexReader.close();//关闭旧的reader
					
					indexReader=directoryReader;
					indexReaders.remove(configName);//删除旧的reader
					
					indexReaders.put(configName, indexReader);
				}
			}
		} catch (IOException e) {
			log.error("打开IndexReader失败",e);
		}
		return indexReader;
	}
	
	/**
	 * 关闭indexReader
	 * @param configName
	 */
	public static void close(String configName){
		try {
			DirectoryReader indexReader = indexReaders.get(configName);
			if(indexReader!=null){
				indexReader.close();
				indexReader=null;
				
				indexReaders.remove(configName);
			}
		} catch (IOException e) {
			log.error("关闭IndexReader失败",e);
		}
	}
}
