package com.kingbase.lucene.commons.indexer.indexWriter;

import com.kingbase.lucene.commons.configuration.ReadConfig;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class IndexWriterFactory {

	private static final Logger log = Logger.getLogger(IndexWriterFactory.class);
	private static final ConcurrentHashMap<Object, IndexWriter> indexWriters = new ConcurrentHashMap<Object, IndexWriter>();

	/**
	 * @param configName
	 * @return
	 */
	public static IndexWriter create(String configName) {
		IndexWriter indexWriter = indexWriters.get(configName);
		try {
			if (indexWriter == null || !indexWriter.isOpen()) {
				// 获取配置信息
				ReadConfig config = new ReadConfig(configName);
				Analyzer analyzer = config.getAnalyzer();
				Directory directory = config.getDirectory();
				
				IndexWriterConfig conf = new IndexWriterConfig(analyzer);
				indexWriter = new IndexWriter(directory, conf);
				indexWriters.put(configName, indexWriter);
			}
		} catch (IOException e) {
			log.error("打开IndexWriter失败", e);
		}
		return indexWriter;
	}

	/**
	 * 关闭indexWriter
	 * @param configName 配置名称
	 */
	public static void close(String configName) {
		try {
			IndexWriter indexWriter = indexWriters.get(configName);
			if(indexWriter!=null&&indexWriter.isOpen()){
				indexWriter.commit();
				indexWriter.close();
				
				indexWriter=null;
				
				indexWriters.remove(configName);
			}
		} catch (IOException e) {
			log.error("关闭IndexWriter失败", e);
		}
	}
	
	/**
	 * 清空indexWriter
	 */
	public static void clear() {
		try {
			Set<Entry<Object,IndexWriter>> entrySet = indexWriters.entrySet();
			for (Entry<Object, IndexWriter> entry : entrySet) {
				IndexWriter indexWriter = entry.getValue();
				indexWriter.close();
				
				indexWriter=null;
			}
			indexWriters.clear();//清空map
		} catch (IOException e) {
			log.error("关闭IndexWriter失败", e);
		}
	}
	
	public static void rollback(String configName){
		try {
			IndexWriter indexWriter = indexWriters.get(configName);
			if(indexWriter!=null&&indexWriter.isOpen()){
				indexWriter.rollback();
			}
		} catch (IOException e) {
			log.error("IndexWriter回滚失败", e);
		}
	}
}
